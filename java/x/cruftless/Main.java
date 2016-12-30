package x.cruftless;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Main extends Activity {

class Value<Type> {
	final Type value;
	Value(Type value) {
		this.value = value;
	}
	public boolean equals(Object o) {
		return (o == this) || (o != null && getClass().isInstance(o) && ((Value)o).value.equals(value));
	}
	public int hashCode() {
		return value.hashCode();
	}
}

class Identifier extends Value<Integer> {
	Identifier(Integer i) { super(i == null ? 0 : i); }
}

class Runes extends Value<String> {
	Runes(String s) { super(s == null ? "" : s); }
}

@Override
public
void onCreate (Bundle b) {
	super.onCreate(b);
	setContentView(R.layout.main);
}

View from (Identifier i) {
	return findViewById(i.value);
}

}
