package isep.cma.projecto;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class FragmentRoom1 extends Fragment {

	private RelativeLayout layout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		layout = (RelativeLayout) inflater.inflate(R.layout.room, container, false);

		return layout;
	}
}
