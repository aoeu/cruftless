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

class PositiveRange extends Value<Integer> {
	static final int empty = 0;
	static final int min = 1;
	final int max;

	PositiveRange(Integer value,  Integer  max) {
		super(value < PositiveRange.min ||  value > max ? PositiveRange.empty : value);
		this.max = max;
	}
}

class Month extends PositiveRange {
	static final int max = 12;
	Month(Integer value) {
		super(value, max);
	}
	Month() {
		super(empty, max);
	}
}

class Day extends PositiveRange {
	static final int max = 31;
	Day(Integer value) {
		super(value, max);
	}
	Day() {
		super(empty, max);
	}
}

class Date {
	final Day day;
	final Month month;
	Date(Month m, Day d) {
		month = m == null ? new Month() : m;
		day = d == null ? new Day() : d;
	}
	Date() {
		this(null, null);
	}
}

String[] getMonthNames() {
	final int extraLengthForDefaultEmptyValue = 1;
	String[] names = new String[Month.max + extraLengthForDefaultEmptyValue];
	names[Month.empty] = "―";
	final int startIndexOfArrayToCopy = 0;
	System.arraycopy(new DateFormatSymbols().getMonths(), startIndexOfArrayToCopy, names, Month.min, Month.max);
	return names;
}

String[] getDaysAsStrings() {
	final int extraLengthForDefaultEmptyValue = 1;
	String[] s = new String[Day.max + extraLengthForDefaultEmptyValue];
	s[Day.empty] = "―";
	for (int i = Day.min; i <= Day.max; i++) {
		s[i] = Integer.toString(i);
	}
	return s;
}

void init(NumberPicker picker, PositiveRange range, String[] displayedValues) {
	final int extraSpaceForDefaultEmptyValue;
	picker.setMinValue(range.empty);
	picker.setMaxValue(range.max);
	picker.setValue(range.value);
	picker.setDisplayedValues(displayedValues);
}


@Override
public
void onCreate (Bundle b) {
	super.onCreate(b);
	setContentView(R.layout.main);

	Date date = new Date();

	NumberPicker monthPicker = (NumberPicker) from (  new Identifier ( R.id.month ) );
	init(monthPicker, date.month, getMonthNames());

	NumberPicker dayPicker = (NumberPicker) from ( new Identifier ( R.id.day ) );
	init(dayPicker, date.day, getDaysAsStrings());
}

View from (Identifier i) {
	return findViewById(i.value);
}

}
