package injection;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.bcel.Constants;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.BIPUSH;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.ConstantPushInstruction;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.FieldInstruction;
import org.apache.bcel.generic.GETFIELD;
import org.apache.bcel.generic.GETSTATIC;
import org.apache.bcel.generic.IADD;
import org.apache.bcel.generic.IASTORE;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.IDIV;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.ILOAD;
import org.apache.bcel.generic.IMUL;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.IRETURN;
import org.apache.bcel.generic.ISHL;
import org.apache.bcel.generic.ISTORE;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionFactory;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.MULTIANEWARRAY;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.PUSH;
import org.apache.bcel.generic.PUTFIELD;
import org.apache.bcel.generic.PUTSTATIC;
import org.apache.bcel.generic.SIPUSH;
import org.apache.bcel.generic.Type;

import environment.Data;

public class XteaInjector extends URLClassLoader {

	private String name;
	private Map<String, ClassGen> entries = new HashMap<String, ClassGen>();
	private Map<String, byte[]> classes = new HashMap<String, byte[]>();
	private String[] data = new String[2];

    public XteaInjector(URL urls, String jarName) {
        super(new URL[]{urls});
        name = jarName;
		try {
			JarFile jf = new JarFile(new File(name+".jar"));
			Enumeration<JarEntry> jarEntries = jf.entries();
			while (jarEntries.hasMoreElements()) {
				JarEntry entry = jarEntries.nextElement();
				String entryName = entry.getName();
				if (entryName.endsWith(".class")) {
					JavaClass jclazz = new ClassParser(name+".jar", entryName).parse();
					entries.put(entryName.replace(".class", ""), new ClassGen(jclazz));
				}
			}
			JavaClass myClass = Repository.lookupClass("injection.Dumper");
			entries.put("Dumper", new ClassGen(myClass));
			injectCall();
			injectCall2();
			//injectPacketDebugger();
			//injectPacketsDebugger();
			for (ClassGen cg : entries.values()) {
				String name = cg.getClassName().replace(".", File.separator) + ".class";
				classes.put(name, cg.getJavaClass().getBytes());
				Data.LOADER.classBytes.put(name, cg.getJavaClass().getBytes());		
			}

			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    }

	@Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
    	if(!name.contains(".")) {
    		byte[] buffer = classes.get(name.replace('.', '/') + ".class");
    		return defineClass(name, buffer, 0, buffer.length);
    	}
        return super.loadClass(name);
    }
    
	
	public void injectCall() {
		for (ClassGen cg : entries.values()) {
			final ConstantPoolGen cp = cg.getConstantPool();
			if (cp.lookupString("l") != -1) {
				for (Method m : cg.getMethods()) {
					if (m.isStatic() && m.getReturnType().equals(Type.VOID)) {
						InstructionSearcher searcher = new InstructionSearcher(cg, m);
						if (searcher.nextLDC("l") != null) {
							MethodGen mg = new MethodGen(m, cg.getClassName(), cp);
							InstructionList list = mg.getInstructionList();
							searcher.index(-1);
							InstructionSearcher.Constraint iconst_4 = new InstructionSearcher.Constraint() {

								public boolean accept(Instruction instr) {
									return instr instanceof ICONST && ((ICONST) instr).getValue().equals(4);
								}
							};
							FieldInstruction keys = null;
							while (searcher.next(ICONST.class, iconst_4) != null) {
								if (searcher.next() instanceof MULTIANEWARRAY
										&& searcher.next() instanceof PUTSTATIC) {
									keys = (FieldInstruction) searcher.current();
									break;
								}
							}
							
							//System.out.println(keys.getClassName(cp)+":"+keys.getFieldName(cp)+", "+cg.getClassName()+":"+m.getName());
							
							searcher.index(-1);
							int regions2 = 0;
							while (searcher.next(ILOAD.class, null) != null) {
								if (searcher.next() instanceof ILOAD && searcher.next() instanceof BIPUSH && searcher.next() instanceof ISHL
										&& searcher.next() instanceof IADD) {
									Instruction next = searcher.next();
									//System.out.println(next);
									regions2 = ((ISTORE) next).getIndex();
									break;
								}
							}
							
							//System.out.println("index="+regions2);
							

							final FieldInstruction keys_copy = keys;
							//System.out.println((keys == null) + ":"+(cp == null) + ":" + (keys_copy == null) + ": "+ cg.getClassName()+":"+m.getName());
							InstructionSearcher.Constraint is_keys = new InstructionSearcher.Constraint() {

								public boolean accept(Instruction instr) {
									if (!(instr instanceof FieldInstruction)) {
										return false;
									}
									FieldInstruction other = (FieldInstruction) instr;
									return keys_copy.getClassName(cp).equals(other.getClassName(cp))
											&& keys_copy.getFieldName(cp).equals(other.getFieldName(cp));
								}
							};
							searcher.index(-1);
							if (keys == null || searcher.next(FieldInstruction.class, is_keys) == null) {
								continue;
							}
							searcher.nextLDC("l");
							for (int i = 0; i < 2; i++) {
								searcher.previous(GETSTATIC.class, null);
							}
							int index = searcher.next(ILOAD.class, null).getIndex();
							searcher.next(GETSTATIC.class, null);
							InstructionFactory fact = new InstructionFactory(cg, cp);
							InstructionList inject = new InstructionList();
							inject.append(InstructionFactory.createLoad(Type.INT, regions2));
							inject.append(fact.createGetStatic(keys.getClassName(cp), keys.getFieldName(cp),
									Type.getType("[[I")));
							
							inject.append(InstructionFactory.createLoad(Type.INT, index));
							inject.append(InstructionFactory.createArrayLoad(Type.getType("[[I")));
							inject.append(fact.createInvoke("injection.Dumper", "dump", Type.VOID,
									new Type[] {Type.INT, Type.getType("[I")}, Constants.INVOKESTATIC));
							list.insert(list.findHandle(searcher.currentHandle().getPosition()), inject);
							list.setPositions();
							mg.setInstructionList(list);
							mg.setMaxLocals();
							mg.setMaxStack();
							cg.replaceMethod(m, mg.getMethod());
							System.out.println("Added call in " + cg.getClassName() + "." + m.getName() + m.getSignature());
						}
					}
				}
			}
		}
	}
	
	public void injectCall2() {
		for (ClassGen cg : entries.values()) {
			final ConstantPoolGen cp = cg.getConstantPool();
			if (cp.lookupString("m") != -1) {
				for (Method m : cg.getMethods()) {
					if (m.isStatic() && m.getReturnType().equals(Type.VOID)) {
						InstructionSearcher searcher = new InstructionSearcher(cg, m);
						if (searcher.nextLDC("m") != null) {
							MethodGen mg = new MethodGen(m, cg.getClassName(), cp);
							InstructionList list = mg.getInstructionList();
							searcher.index(-1);
							InstructionSearcher.Constraint iconst_4 = new InstructionSearcher.Constraint() {

								public boolean accept(Instruction instr) {
									return instr instanceof ICONST && ((ICONST) instr).getValue().equals(4);
								}
							};
							
							
							FieldInstruction keys = null;
							while (searcher.next(ICONST.class, iconst_4) != null) {
								if (searcher.next() instanceof MULTIANEWARRAY
										&& searcher.next() instanceof PUTSTATIC) {
									keys = (FieldInstruction) searcher.current();
									break;
								}
							}
							
							searcher.index(-1);
							
							int index2 = 0;
							
							/*   //   198: iload 12
						    //   200: bipush 8
						    //   202: idiv
						    //   203: bipush 8
						    //   205: ishl
						    //   206: iload 13
						    //   208: bipush 8
						    //   210: idiv
						    //   211: iadd
						    //   212: istore 14
							
							while (searcher.next(ILOAD.class, null) != null) {
								if(searcher.next() instanceof BIPUSH && searcher.next() instanceof IDIV && searcher.next() instanceof BIPUSH && searcher.next() instanceof ISHL && 
										searcher.next() instanceof ILOAD && searcher.next() instanceof BIPUSH && searcher.next() instanceof IDIV && searcher.next() instanceof IADD) {
									index2 = ((ISTORE) searcher.next()).getIndex();
								}
							}*/
							
							/*while (searcher.next(GETSTATIC.class, null) != null) {
							 // 							
							 // 565: getstatic 227	cx:dv	[I
						    //   568: iload_3
						    //   569: iload 11
						    //   571: iastore				 
								if(searcher.next() instanceof ILOAD && searcher.next() instanceof ILOAD && searcher.next() instanceof IASTORE) {
									//System.out.println(searcher.previous());
									index2 = ((ILOAD) searcher.previous()).getIndex();
									System.out.println(index2);
								}
							}*/
							
							//System.out.println(keys.getClassName(cp)+":"+keys.getFieldName(cp)+", "+cg.getClassName()+":"+m.getName());

							final FieldInstruction keys_copy = keys;
							//System.out.println((keys == null) + ":"+(cp == null) + ":" + (keys_copy == null) + ": "+ cg.getClassName()+":"+m.getName());
							InstructionSearcher.Constraint is_keys = new InstructionSearcher.Constraint() {

								public boolean accept(Instruction instr) {
									if (!(instr instanceof FieldInstruction)) {
										return false;
									}
									FieldInstruction other = (FieldInstruction) instr;
									return keys_copy.getClassName(cp).equals(other.getClassName(cp))
											&& keys_copy.getFieldName(cp).equals(other.getFieldName(cp));
								}
							};
							searcher.index(-1);
							if (keys == null || searcher.next(FieldInstruction.class, is_keys) == null) {
								continue;
							}
							/*searcher.index(-1);
							while (searcher.next(GETSTATIC.class, null) != null) {
							 // 							
							 // 565: getstatic 227	cx:dv	[I
						    //   568: iload_3
						    //   569: iload 11
						    //   571: iastore				 
								if(searcher.next() instanceof ILOAD && searcher.next() instanceof ILOAD && searcher.next() instanceof IASTORE) {
									//System.out.println(searcher.previous());
									index2 = ((ILOAD) searcher.previous()).getIndex();
									System.out.println(index2);
									break;
								}
							}*/
							searcher.index(-1);
							searcher.nextLDC("m");
							while (searcher.next(GETSTATIC.class, null) != null) {
								 // 							
								 // 565: getstatic 227	cx:dv	[I
							    //   568: iload_3
							    //   569: iload 11
							    //   571: iastore				 
									if(searcher.next() instanceof ILOAD && searcher.next() instanceof ILOAD && searcher.next() instanceof IASTORE) {
										//System.out.println(searcher.previous());
										index2 = ((ILOAD) searcher.previous()).getIndex();
										//System.out.println(index2);
										break;
									}
								}
							searcher.nextLDC("m");
							for (int i = 0; i < 2; i++) {
								searcher.previous(GETSTATIC.class, null);
							}
							int index = searcher.next(ILOAD.class, null).getIndex();
							searcher.next(GETSTATIC.class, null);
							InstructionFactory fact = new InstructionFactory(cg, cp);
							InstructionList inject = new InstructionList();
							inject.append(InstructionFactory.createLoad(Type.INT, index2));
							inject.append(fact.createGetStatic(keys.getClassName(cp), keys.getFieldName(cp),
									Type.getType("[[I")));
							
							inject.append(InstructionFactory.createLoad(Type.INT, index));
							inject.append(InstructionFactory.createArrayLoad(Type.getType("[[I")));
							inject.append(fact.createInvoke("injection.Dumper", "dump", Type.VOID,
									new Type[] {Type.INT, Type.getType("[I")}, Constants.INVOKESTATIC));
							list.insert(list.findHandle(searcher.currentHandle().getPosition()), inject);
							list.setPositions();
							mg.setInstructionList(list);
							mg.setMaxLocals();
							mg.setMaxStack();
							cg.replaceMethod(m, mg.getMethod());
							System.out.println("Added call2 in " + cg.getClassName() + "." + m.getName() + m.getSignature());
						}
					}
				}
			}
		}
	}
	
	/**
	  //   2809: invokevirtual 1523	java/lang/String:charAt	(I)C
	  //   2812: bipush 115
	  //   2814: if_icmpne +35 -> 2849
	  //   2817: iload_0
	  //   2818: bipush 25
	 */
	public void injectPacketDebugger() {
		for (ClassGen cg : entries.values()) {
			final ConstantPoolGen cp = cg.getConstantPool();
			//if (cp.lookupString("Cabbage") != -1) {
				for (Method m : cg.getMethods()) {
					if (m.isStatic() && m.getReturnType().equals(Type.BOOLEAN)) {
						MethodGen mg = new MethodGen(m, cg.getClassName(), cp);
						InstructionList list = mg.getInstructionList();
						InstructionSearcher searcher = new InstructionSearcher(cg, m);
						InstructionSearcher.Constraint bipush_4 = new InstructionSearcher.Constraint() {

							public boolean accept(Instruction instr) {
								return instr instanceof BIPUSH && ((BIPUSH) instr).getValue().equals(115);
							}
						};
						boolean packetMethod = false;
						while (searcher.next(BIPUSH.class, bipush_4) != null) {
							packetMethod = true;
							break;
						}
						if(packetMethod) {
							searcher.index(-1);
							int multiplier = 0;
							FieldInstruction packetId = null;
							FieldInstruction packetData = null;
							InstructionSearcher.Constraint iconst_4 = new InstructionSearcher.Constraint() {

								public boolean accept(Instruction instr) {
									return instr instanceof ICONST && ((ICONST) instr).getValue().equals(0);
								}
							};
							while (searcher.next(ICONST.class, iconst_4) != null) {
								if(searcher.next(PUTFIELD.class, null) != null && searcher.next(GETSTATIC.class, null) != null && searcher.next(SIPUSH.class, null) != null && searcher.next(INVOKEVIRTUAL.class, null) != null && searcher.next(LDC.class, null) != null) {
									if(searcher.next() instanceof IMUL && searcher.next() instanceof PUTSTATIC) {
										packetId = (FieldInstruction) searcher.current();
										data[0] = packetId.getClassName(cp);
										data[1] = packetId.getFieldName(cp);
									}
								}
								break;
							}
							while (searcher.next(GETSTATIC.class, null) != null) {
								if(searcher.next() instanceof GETSTATIC && searcher.next() instanceof LDC) {
									Instruction current = searcher.current();
									multiplier = (int) ((LDC) current).getValue(cp);
								}
								break;
							}
							searcher.index(-1);
							while (searcher.next(GETSTATIC.class, null) != null) {
								if(searcher.next(GETSTATIC.class, null) != null && searcher.next(GETFIELD.class, null) != null) {
									Instruction current = searcher.current();
									packetData = (FieldInstruction) current;
								}
								break;
							}
							if(packetId != null) {
								LDC ldc = new LDC(cp.addInteger(multiplier));
								System.out.println("Found multiplier "+multiplier+" and packet_type "+packetId.getClassName(cp)+"."+packetId.getFieldName(cp));
								InstructionFactory fact = new InstructionFactory(cg, cp);
								InstructionList inject = new InstructionList();
								
								inject.append(ldc);
								inject.append(fact.createGetStatic(packetId.getClassName(cp), packetId.getFieldName(cp), Type.INT));
								
								inject.append(fact.createInvoke("injection.Dumper", "dumpPacket", Type.VOID,
										new Type[] {Type.INT, Type.INT}, Constants.INVOKESTATIC));
								
								if(packetData != null) {
									inject.append(fact.createGetStatic(packetData.getClassName(cp), packetData.getFieldName(cp), Type.getType(Byte[].class)));
									
									inject.append(fact.createInvoke("injection.Dumper", "dumpPacketData", Type.VOID,
											new Type[] {Type.getType(Byte[].class)}, Constants.INVOKESTATIC));
								}
								
								list.insert(list.findHandle(searcher.currentHandle().getPosition()), inject);
								list.setPositions();
								mg.setInstructionList(list);
								mg.setMaxLocals();
								mg.setMaxStack();
								cg.replaceMethod(m, mg.getMethod());
							}
							System.out.println("Added packet call in " + cg.getClassName() + "." + m.getName() + m.getSignature());
						}
					}
				}
			}
		//}
	}
	
	public void injectPacketsDebugger() {
		for (ClassGen cg : entries.values()) {
			final ConstantPoolGen cp = cg.getConstantPool();
			if (cp.lookupString("Cabbage") != -1) {
				for (Method m : cg.getMethods()) {
					if (m.isStatic() && m.getReturnType().equals(Type.BOOLEAN)) {
						MethodGen mg = new MethodGen(m, cg.getClassName(), cp);
						InstructionList list = mg.getInstructionList();
						InstructionSearcher searcher = new InstructionSearcher(cg, m);
						InstructionSearcher.Constraint bipush_4 = new InstructionSearcher.Constraint() {

							public boolean accept(Instruction instr) {
								return instr instanceof BIPUSH && ((BIPUSH) instr).getValue().equals(115);
							}
						};
						boolean packetMethod = false;
						while (searcher.next(BIPUSH.class, bipush_4) != null) {
							packetMethod = true;
							break;
						}
						if(packetMethod) {
							searcher.index(-1);
							InstructionSearcher.Constraint headerstatic = new InstructionSearcher.Constraint() {

								public boolean accept(Instruction instr) {
									if (!(instr instanceof FieldInstruction)) {
										return false;
									}
									FieldInstruction other = (FieldInstruction) instr;
									return data[0].equals(other.getClassName(cp))
											&& data[1].equals(other.getFieldName(cp));
								}
							};
							boolean procced = false;
							int pattern1 = 0;
							int pattern2 = 0;
							int pattern3 = 0;
							int pattern4 = 0;
							while((searcher.next(BIPUSH.class, null) != null || searcher.next(SIPUSH.class, null) != null)) {
								if(searcher.next() instanceof LDC && searcher.next(GETSTATIC.class, headerstatic) != null && searcher.next() instanceof IMUL && searcher.next() instanceof IF_ICMPNE) {
									pattern1++;
									procced = true;
								}
							}
							searcher.index(-1);
							while(searcher.next(GETSTATIC.class, headerstatic) != null) {
								if(searcher.next() instanceof LDC && searcher.next() instanceof IMUL && (searcher.next() instanceof BIPUSH || searcher.next() instanceof SIPUSH) && searcher.next() instanceof IF_ICMPNE) {
									pattern2++;
									procced = true;
								}
							}
							searcher.index(-1);
							while(searcher.next(GETSTATIC.class, headerstatic) != null) {
								if(searcher.next() instanceof IMUL && (searcher.next() instanceof BIPUSH || searcher.next() instanceof SIPUSH) && searcher.next() instanceof IF_ICMPNE) {
									pattern3++;
									procced = true;
								}
							}
							searcher.index(-1);
							while((searcher.next(BIPUSH.class, null) != null || searcher.next(SIPUSH.class, null) != null)) {//searcher.next(GETSTATIC.class, headerstatic) != null) {
								if(searcher.next(GETSTATIC.class, headerstatic) != null && searcher.next() instanceof LDC && searcher.next() instanceof IMUL && searcher.next() instanceof IF_ICMPNE) {
									pattern4++;
									procced = true;
								}
							}
							if(procced) {
								System.out.println("Found pattern1 "+pattern1+", pattern2 "+pattern2+", pattern3 "+pattern3+" and pattern4 "+pattern4+" packets.");
								searcher.index(-1);
							}
							//System.out.println("Found "+whalecount+" whales in sir tom basement.");
							//System.out.println("Added packet call in " + cg.getClassName() + "." + m.getName() + m.getSignature());
						}
					}
				}
			}
		}
	}
    
}
