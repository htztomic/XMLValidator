package xmlvalidator;

import java.util.*;

public class BasicStringStack implements StringStack {
	private ArrayList<String> Stack = new ArrayList<String>();

	@Override
	public void push(String item) {
		Stack.add(item);
		// TODO Auto-generated method stub

	}

	public String remove(int position) {
		if (Stack.size() == 0) {
			return null;
		} else {
			return Stack.remove((Stack.size() - 1) - position);
		}

	}

	public String pop() {
		// TODO Auto-generated method stub
		if (Stack.size() == 0) {
			return null;
		} else {
			return Stack.remove(Stack.size() - 1);
		}
	}

	@Override
	public String peek(int position) {

		// TODO Auto-generated method stub
		return Stack.get((Stack.size() - 1) - position);
	}

	@Override
	public int getCount() {

		// TODO Auto-generated method stub
		return Stack.size();
	}

}
