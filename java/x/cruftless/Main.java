package x.cruftless;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;

import java.text.DateFormatSymbols;

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

	NumberPicker month = (NumberPicker) from (  new Identifier ( R.id.month ) );
	month.setMinValue(0);
	month.setMaxValue(12);
	String[] months = new String[13];
	months[0] = "―";
	System.arraycopy(new DateFormatSymbols().getMonths(), 0, months, 1, 12);
	month.setDisplayedValues(months);

	String[] days = new String[32];
	days[0] = "―";
	for (int i = 1; i < days.length; i++) {
		days[i] = Integer.toString(i);
	}
	NumberPicker day = (NumberPicker) from ( new Identifier ( R.id.day ) );
	day.setMinValue(0);
	day.setMaxValue(31);
	day.setDisplayedValues(days);
}

View from (Identifier i) {
	return findViewById(i.value);
}

}
