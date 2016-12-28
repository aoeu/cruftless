package aoeu.cruftless;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

class	Identifier	extends	Value<Integer>	{	Identifier	(Integer i)	{	super(i == null ? 0 : i);	}	}
class	Runes	extends	Value<String>	{	Runes	(String s)	{	super(s == null ? "" : s);	}	}
class	AssetPath	extends	Runes		{	AssetPath	(String s)	{	super(s);				}	}

@Override
public
void
onCreate
(
	Bundle b
) 
{
	super
		.onCreate(
			b
		);
	init();
}

void 
init
()
{
	setContentView(
		R.layout.main
	);
	( 
		(TextView) 
		from	(
			new Identifier(
				R.id.nothingToSeeHere
			)
		)
	).setTypeface(
			from(
				new AssetPath(
					"Go-Regular.ttf"
				)
			)
	); 
}

View 
from
(
	Identifier i
)
{
    	return 
		findViewById(
			i.value
		);
}

Typeface
from 
(
	AssetPath a
)
{
	try {
		return 
			Typeface
				.createFromAsset(
					getAssets(), 
					a.value
				);
	} catch (
		RuntimeException 
			exceptionForAssetPathNotFound
	) {
		return 
			Typeface
				.DEFAULT;
	}
}

}
