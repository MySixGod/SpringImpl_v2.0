package spring.SpringImpl;

public class son extends chouxiang{
	
	//具体实现放在son类中，抽象类只实现一部分
	@Override
	public int jishu(String strs) {
		return strs.length();
	}

    public static void main(String[] args) {
		son s=new son();
		String[]  strs={"aaa","bbb"};
		System.out.println(s.jishu(strs));
	}
}
