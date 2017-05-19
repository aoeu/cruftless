package x.cruftless;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.animation.ObjectAnimator;
import android.view.animation.DecelerateInterpolator;

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

ObjectAnimator anim;
ProgressBar ring;

@Override
public
void onCreate (Bundle b) {
	super.onCreate(b);
	setContentView(R.layout.main);

	int valueToStartAt = getResources().getInteger(R.integer.progress_bar_initial_value);
	int valueToEndAt = getResources().getInteger(R.integer.progress_bar_max_value);

	anim = ObjectAnimator
		.ofInt(
			(ProgressBar)from(new Identifier(R.id.progressBar)),
			"progress",
			valueToStartAt,
			valueToEndAt
		);

	anim.setDuration(5000);
	anim.setInterpolator(new DecelerateInterpolator());
}


public void animateProgressRing(View v) {
	anim.start();
}

View from (Identifier i) {
	return findViewById(i.value);
}

}
