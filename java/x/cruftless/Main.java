package x.cruftless;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.text.DateFormatSymbols;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

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

class Ordinal extends Value<Integer> {
	static final int EMPTY = 0;
	static final int MIN = 1;
	final int max;

	Ordinal(Integer value,  Integer  max) {
		super(value < Ordinal.MIN ||  value > max ? Ordinal.EMPTY : value);
		this.max = max;
	}

	String[] getValueRangeAsStrings() {
		final int extraLengthForDefaultEmptyValue = 1;
		String[] s = new String[max + extraLengthForDefaultEmptyValue];
		s[EMPTY] = "―";
		for (int i = MIN; i <= max; i++) {
			s[i] = Integer.toString(i);
		}
		return s;
	}

}

class Month extends Ordinal {
	static final int max = 12;
	Month(Integer value) {
		super(value, max);
	}
	Month() {
		super(EMPTY, max);
	}

	String[] getValueRangeAsStrings() {	
		final int extraLengthForDefaultEmptyValue = 1;
		String[] names = new String[Month.max + extraLengthForDefaultEmptyValue];
		names[Month.EMPTY] = "―";
		final int startIndexOfArrayToCopy = 0;
		System.arraycopy(new DateFormatSymbols().getMonths(), startIndexOfArrayToCopy, names, Month.MIN, Month.max);
		return names;
	}
}

Map<Integer, Ordinal> putDaysPerMonth(Map<Integer,Ordinal> m, Calendar c) {
	int indexOffset = 1;
	for (int i = c.getMinimum(Calendar.MONTH); i <= c.getMaximum(Calendar.MONTH); i++) {
		c.set(Calendar.MONTH, i);
		m.put(i+indexOffset, new Ordinal(0, c.getActualMaximum(Calendar.DAY_OF_MONTH)));
	}
        return Collections.unmodifiableMap(m);
}

Map<Integer, Ordinal> getDaysPerGregorianMonthWithLeapYear() {
	int leapYear = 2004;
	int day = 1;
	int month = 1;
	Map<Integer, Ordinal> map = new HashMap<>();
	map.put(Month.EMPTY, new Day());
	return putDaysPerMonth(map, new GregorianCalendar(leapYear, month, day));
}

class Day extends Ordinal {
	static final int max = 31;
	Day(Integer value) {
		super(value, max);
	}
	Day() {
		super(EMPTY, max);
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

void init(NumberPicker picker, Ordinal ordinal) {
	picker.setMinValue(Ordinal.EMPTY);
	picker.setMaxValue(ordinal.max);
	picker.setValue(determineValue(picker, ordinal));
	if (picker.getDisplayedValues() == null) {
		picker.setDisplayedValues(ordinal.getValueRangeAsStrings());
	}
}

int determineValue(NumberPicker picker, Ordinal ordinal) {
	return  picker.getValue() == 0 
		? ordinal.value 
		:  picker.getValue() >= ordinal.max
			? ordinal.max
			:  picker.getValue();	
}


@Override
public
void onCreate (Bundle b) {
	super.onCreate(b);
	setContentView(R.layout.main);

	Date date = new Date();

	final NumberPicker dayPicker = (NumberPicker) from ( new Identifier ( R.id.day ) );
	init(dayPicker, date.day);

	NumberPicker monthPicker = (NumberPicker) from (  new Identifier ( R.id.month ) );
	init(monthPicker, date.month);

	monthPicker.setOnValueChangedListener(
		new NumberPicker.OnValueChangeListener() {
			Map<Integer, Ordinal> d = getDaysPerGregorianMonthWithLeapYear();
			public void onValueChange(NumberPicker picker, int prev, int next) {

				String s =  String.format("prev %d / %s, next %d / %s", prev, d.get(prev).value, next, d.get(next).max);

				Toast.makeText(Main.this, s, Toast.LENGTH_SHORT).show();

				if (prev != next && d.get(prev).max != d.get(next).max) {
					Main.this.init(dayPicker, d.get(next));
				}

			}
		}
	);

}



public
void submit(View v) {
	NumberPicker monthPicker = (NumberPicker) from (  new Identifier ( R.id.month ) );
	NumberPicker dayPicker = (NumberPicker) from ( new Identifier ( R.id.day ) );
	Date d = new Date(new Month(monthPicker.getValue()), new Day(dayPicker.getValue()));
	Toast.makeText(this, "Month:	" + d.month.value + "	Day:	 "  + d.day.value, Toast.LENGTH_SHORT).show();
}

View from (Identifier i) {
	return findViewById(i.value);
}

}
