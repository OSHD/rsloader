package injection;

import org.apache.bcel.generic.*;
import org.apache.bcel.classfile.Method;

public class InstructionSearcher {

	public static interface Constraint {

		public boolean accept(Instruction instr);

	}

	private InstructionList list;
	private Instruction[] instructions;
	private ConstantPoolGen cpg;
	private int index;

	public InstructionSearcher(ClassGen cg, Method m) {
		cpg = cg.getConstantPool();
		list = new InstructionList(m.getCode().getCode());
		instructions = list.getInstructions();
		index = -1;
	}

	public void index(int index) {
		this.index = index;
	}

	public Instruction current() {
		return (index < 0 || index >= instructions.length) ? null : instructions[index];
	}

	public InstructionHandle currentHandle() {
		return list.getInstructionHandles()[index];
	}

	public Instruction next() {
		++index;
		return current();
	}

	public Instruction previous() {
		--index;
		return current();
	}

	public <T> T next(Class<T> type, Constraint constr) {
		while (++index < instructions.length) {
			Instruction instr = instructions[index];
			if (type.isAssignableFrom(instr.getClass()) && (constr == null || constr.accept(instr))) {
				return type.cast(instr);
			}
		}
		return null;
	}

	public <T> T previous(Class<T> type, Constraint constr) {
		while (--index >= 0) {
			Instruction instr = instructions[index];
			if (type.isAssignableFrom(instr.getClass()) && (constr == null || constr.accept(instr))) {
				return type.cast(instr);
			}
		}
		return null;
	}

	public LDC nextLDC(final Object val) {
		Constraint constraint = new Constraint() {
			public boolean accept(Instruction instr) {
				LDC ldc = (LDC) instr;
				return ldc.getValue(cpg).equals(val);
			}
		};
		return next(LDC.class, constraint);
	}

	public ConstantPushInstruction nextConstant(final int... values) {
		Constraint constraint = new Constraint() {
			public boolean accept(Instruction instr) {
				ConstantPushInstruction cpi = (ConstantPushInstruction) instr;
				for (int val : values) {
					if (cpi.getValue().equals(val)) {
						return true;
					}
				}
				return false;
			}
		};
		return next(ConstantPushInstruction.class, constraint);
	}

	public ConstantPushInstruction previousConstant(final int... values) {
		Constraint constraint = new Constraint() {
			public boolean accept(Instruction instr) {
				ConstantPushInstruction cpi = (ConstantPushInstruction) instr;
				for (int val : values) {
					if (cpi.getValue().equals(val)) {
						return true;
					}
				}
				return false;
			}
		};
		return previous(ConstantPushInstruction.class, constraint);
	}

}
