package apprestart.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private GridLayout gridLayout;
    private TextView textView;
    private Handler handler;
    private int counter = 0;
    private List<Button> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
    }
    /*初始化*/
    public void initButtons() {
        textView = (TextView) findViewById(R.id.count);
        handler = new Handler();
        gridLayout = (GridLayout) findViewById(R.id.gridlayout);
        /*動態加載Button*/
        for (int i = 0; i < 9; i++) {
            Button button = new Button(this);
            button.setOnClickListener(this);
            /*將Button放入List裡*/
            list.add(button);
            /*將List放入GridLayout*/
            gridLayout.addView(button);
        }
    }
    /*啟動Runnable*/
    public void Start(View view) {
        textView.setText("次數 :");
        counter = 0;
        handler.post(x_random_runnable);
        handler.postDelayed(x_random_runnableStop, 10000);
    }
    private X_Random_Runnable x_random_runnable = new X_Random_Runnable();
    private X_Random_RunnableStop x_random_runnableStop = new X_Random_RunnableStop();
    /*實現onClickListener*/
    @Override
    public void onClick(View v) {
        /*判斷按下的Button是不是 X*/
        if ("X".equals(((Button) v).getText().toString())) {
            ++counter;
            textView.setText("擊中" + counter + "次數");
            initButtonSetText();
        }
    }
    /*讓X消失*/
    private void initButtonSetText() {
        for(int i=0;i<list.size();i++){
            list.get(i).setText("");
        }
    }

    /*實現Runnable 亂數跑 9個Button*/
    private class X_Random_Runnable implements Runnable {

        @Override
        public void run() {
            int index = (int) (Math.random() * list.size());
            for (int i = 0; i < list.size(); i++) {
                if (i == index) {
                    list.get(i).setText("X");
                } else {
                    list.get(i).setText("");
                }
            }
            handler.postDelayed(this, 1500);
        }
    }
    /*停止Runnable*/
    private class X_Random_RunnableStop implements Runnable {

        @Override
        public void run() {
            handler.removeCallbacks(x_random_runnable);
        }
    }
}
