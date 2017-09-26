package sage.lu6gmail.com.hangtest.observerpattern;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sage.lu6gmail.com.hangtest.R;

public class LtActivity extends AppCompatActivity implements ObserverListener {
    private TextView tv_lt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lt);
        tv_lt = (TextView) findViewById(R.id.tv_lt);
        Button btn_lt = (Button) findViewById(R.id.btn_lt);
        btn_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObserverManager.getInstance().notifyObserver("观察者请刷新信息");
            }
        });
        ObserverManager.getInstance().add(this);
    }

    @Override
    public void observerUpData(String content) {
        tv_lt.setText(content);
    }
}
