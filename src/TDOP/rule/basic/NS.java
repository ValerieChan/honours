package TDOP.rule.basic;
import TDOP.rule.AbstractRule;
import TDOP.rule.Operation;
import TDOP.rule.WorkCenter;
import TDOP.simulation.state.SystemState;
public class NS  extends AbstractRule{
	
	public NS(){
		name = "\"NS\"";
	}

	@Override
	public double priority(Operation op, WorkCenter workCenter,
			SystemState systemState) {
		// TODO Auto-generated method stub
		return 0;
	}

}
