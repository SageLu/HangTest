package sage.lu6gmail.com.hangtest.observerpattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import sage.lu6gmail.com.hangtest.R;

public class ZLActivity extends Activity implements ObserverListener {
    private TextView tv_zl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zl);
        tv_zl = (TextView) findViewById(R.id.tv_zl);
        Button btn_zl = (Button) findViewById(R.id.btn_zl);
        btn_zl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ZLActivity.this,LtActivity.class);
                startActivity(intent);
            }
        });
        ObserverManager.getInstance().add(this);

    }

    @Override
    public void observerUpData(String content) {
        tv_zl.setText(content);
    }
}
