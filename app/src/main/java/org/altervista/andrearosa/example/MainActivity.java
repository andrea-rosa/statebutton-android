package org.altervista.andrearosa.example;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.Random;

import org.altervista.andrearosa.statebutton.StateButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout container;
    private Random rnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rnd = new Random();

        container = (LinearLayout) findViewById(R.id.container);

        for(int i = 0; i < container.getChildCount(); i++) {
            StateButton btn = (StateButton) container.getChildAt(i);
            btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        final StateButton btn = (StateButton) view;
        if (btn.getState() == StateButton.BUTTON_STATES.ENABLED) {
            new TimerTask(1500, new TimerListener() {
                @Override
                public void afterTimeElapsed() {
                    new TimerTask(1000, new TimerListener() {
                        @Override
                        public void afterTimeElapsed() {
                            btn.setState(StateButton.BUTTON_STATES.ENABLED);
                        }

                        @Override
                        public void beforeTimerElapsed() {
                            btn.setState(rnd.nextBoolean() ? StateButton.BUTTON_STATES.FAILED : StateButton.BUTTON_STATES.SUCCESS);
                        }
                    }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }

                @Override
                public void beforeTimerElapsed() {
                    btn.setState(StateButton.BUTTON_STATES.LOADING);
                }
            }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.menuSwitch) {

            for(int i = 0; i < container.getChildCount(); i++) {
                StateButton btn = (StateButton) container.getChildAt(i);
                if(btn.getState() == StateButton.BUTTON_STATES.DISABLED)
                    btn.setState(StateButton.BUTTON_STATES.ENABLED);
                else if(btn.getState() == StateButton.BUTTON_STATES.ENABLED)
                    btn.setState(StateButton.BUTTON_STATES.DISABLED);
            }
        }
        if(item.getItemId() == R.id.menuClick) {
            for(int i = 0; i < container.getChildCount(); i++) {
                StateButton btn = (StateButton) container.getChildAt(i);
                if(btn.getState() == StateButton.BUTTON_STATES.ENABLED)
                    btn.performClick();
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    interface TimerListener {
        void afterTimeElapsed();
        void beforeTimerElapsed();
    }

    class TimerTask extends AsyncTask<Void,Void,Void> {

        private long millis;
        private TimerListener listener;

        public TimerTask(long millis, TimerListener listener) {
            this.millis = millis;
            this.listener = listener;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(millis);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.afterTimeElapsed();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.beforeTimerElapsed();
        }
    }
}
