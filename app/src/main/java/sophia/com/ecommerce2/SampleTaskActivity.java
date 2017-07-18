package sophia.com.ecommerce2;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

public class SampleTaskActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_task);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        AsyncTaskSample sample = new AsyncTaskSample();

        sample.execute("sample async task start");
    }

    public class AsyncTaskSample extends AsyncTask<String,Integer,String> {


        @Override
        protected String doInBackground(String... params) {
            for(int i = 0;i<100;i++)
            {
                publishProgress(i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return params[0];
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("onProgressUpdate","progress: " + values);
            progressBar.setProgress(values[0].intValue());
        }
    }
}
