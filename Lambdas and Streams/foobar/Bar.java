/**
 * A simple class that implements a functional interface.
 *
 * @see Foo
 * @see LambdaDemo
 */
public class Bar implements Foo {

	@Override
	public void foo() {
		System.out.println(this.getClass().getTypeName());
	}

}
