package aelitis;

public class OuterClass {
	
	private String member1 = "member1";
	private int member2 = 2;
	
	protected class InnerClass extends OuterClass2 {
		private void accessToOuterMemberInInnerClass() {
			System.out.println(member1);
			System.out.println(member2);
		}
	}
	
	public void makeInnerClassInMemberFunction() {
		InnerClass innerClass = new InnerClass();
		innerClass.accessToOuterMemberInInnerClass();
	}
	
	
	
}
