package fujitora.kaigun_taisho.staysafe;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Button splashToMapButton;
        splashToMapButton=(Button) findViewById(R.id.splashToMapButton);

        splashToMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent splashToMap=new Intent(getApplicationContext(),UserMap.class);
                startActivity(splashToMap);
            }
        });
    }
}
