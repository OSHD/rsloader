
package reflection.cache;


import environment.Data;
import jdk.internal.org.objectweb.asm.tree.ClassNode;
import jdk.internal.org.objectweb.asm.tree.FieldNode;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import reflection.ClassHook;
import reflection.FieldHook;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;

public class Loader {
	private static FieldNode getFieldByName(String fieldName, ClassNode cg){
		for(FieldNode f : cg.fields)
		{
			if(f.name.equals(fieldName))
				return f;
		}
		return null;
	}
    private static Document loadCacheFile(){
        try{
            DocumentBuilderFactory dbFactoryLoad = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilderLoad = dbFactoryLoad.newDocumentBuilder();
            Document docLoad;
            File reflectionCache = new File("reflection_cache.xml");
           // List<String> lines = Files.readAllLines(reflectionCache.toPath());
            //String line = lines.toString();
           // line = line.replaceAll("\n", "");
            docLoad = dBuilderLoad.parse(reflectionCache);
            return docLoad;
        }catch(Exception e){
            return null;
        }
    }

	public static void loadCache(){
		Data.runtimeClassHooks.clear();
		Data.staticFieldHooks.clear();
		try{
			Document docLoad = loadCacheFile();
			Element root = docLoad.getDocumentElement();
            System.out.println("Attempting to load cached reflection model...");
            org.w3c.dom.NodeList classes = root.getChildNodes();
            for(int i=0;i<classes.getLength();++i){
                org.w3c.dom.Node currClass = classes.item(i);
                ClassNode cg = Data.clientClasses.get(currClass.getAttributes().getNamedItem("classname").getNodeValue());
                if(cg==null){
                    System.out.println("Failed to load cached class hook : "+currClass.getNodeName());
                    continue;
                }else{
                    System.out.println("Loaded cached class hook : "+currClass.getNodeName());
                }
                ClassHook currClassHook = new ClassHook(currClass.getNodeName(), cg);
                org.w3c.dom.NodeList fields = currClass.getChildNodes();
                for(int k=0;k<fields.getLength();++k){
                    org.w3c.dom.Node currField = fields.item(k);
                    FieldNode f = getFieldByName(currField.getFirstChild().getNodeValue(), cg);
                    if(f==null){
                        System.out.println("Failed to load cached field hook : "+currField.getNodeName());
                        continue;
                    }
                    currClassHook.addFieldHook(new FieldHook(currField.getNodeName(), f));
                    System.out.println("Loaded cached field hook : "+currField.getNodeName());
                    if(currField.getChildNodes().getLength()>1){
                        org.w3c.dom.NodeList multis = currField.getChildNodes();
                        for(int j=0;j<multis.getLength();++j){
                            try{
                                org.w3c.dom.Node currMulti = multis.item(j);
                                if(currMulti.getNodeName().equals("multiplier")){
                                    int intmulti = Integer.parseInt(currMulti.getFirstChild().getTextContent());
                                    currClassHook.getFieldHook(currField.getNodeName()).setMultiplier(intmulti);
                                    System.out.println("Loaded cached multiplier : "+intmulti);
                                }
                                else if(currMulti.getNodeName().equals("longmultiplier")){
                                    long longmulti = Long.parseLong(currMulti.getFirstChild().getTextContent());
                                    currClassHook.getFieldHook(currField.getNodeName()).setMultiplier(longmulti);
                                    System.out.println("Loaded cached long multiplier : "+longmulti);
                                }
                            }
                            catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
                currClassHook.loadRuntime();
                Data.runtimeClassHooks.put(currClassHook.getRefactoredName(), currClassHook);
            }
            System.out.println("Finishing loading cached reflection model.");
		}catch(Exception e){
			System.out.println("\n\nCache is outdated!");
		}
	}
}
