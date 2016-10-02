package gwrsg_dongsunmac.weather;

/**
 * Created by gwrsg-dongsunmac on 1/10/16.
 */
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by gwrsg-dongsunmac on 1/10/16.
 */

public class ParseTestActivity extends Activity {
    private ArrayList<DataGetterSetters> dataList;
    private DataListAdapter adapter;
    private WeakReference<TextView> txt_title;
    private WeakReference<TextView> txt_description;
    private WeakReference<TextView> txt_item_title;
    private WeakReference<TextView> txt_item_category;
    private WeakReference<TextView> txt_item_forecastissue;
    private WeakReference<TextView> txt_item_validtime;
    private ListView listView;
    private DataGetterSetters data = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        txt_title = new WeakReference<TextView>((TextView)findViewById(R.id.txt_title));
        txt_description = new WeakReference<TextView>((TextView)findViewById(R.id.txt_description));
        txt_item_title = new WeakReference<TextView>((TextView)findViewById(R.id.txt_item_title));
        txt_item_category = new WeakReference<TextView>((TextView)findViewById(R.id.txt_item_category));
        txt_item_forecastissue = new WeakReference<TextView>((TextView)findViewById(R.id.txt_item_forecastissue));
        txt_item_validtime = new WeakReference<TextView>((TextView)findViewById(R.id.txt_item_validtime));

        listView = (ListView)findViewById(R.id.lv_content);
        adapter = new DataListAdapter();
        adapter.setData(getApplicationContext(), new ArrayList<WeatherForecast>());
        listView.setAdapter(adapter);

        Observable observable = Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                String datasetName = "2hr_nowcast";
                String keyref = "781CF461BB6606AD1260F4D81345157F9F25285B80002F64";
                try {
                    String urlString = "http://www.nea.gov.sg/api/WebAPI?dataset=" + datasetName + "&keyref=" + keyref;

                    // Step 2: Call API Url
                    URL obj = new URL(urlString);
                    HttpURLConnection con = (HttpURLConnection)obj.openConnection();
                    con.setRequestMethod("GET");
                    int responseCode = con.getResponseCode();

                    // Step 3: Check the response status
                    if(responseCode == 200) {
                        getXmlData(con.getInputStream());
                        subscriber.onNext(null);
                    } else {
                        subscriber.onError(new Throwable());
                    }

                    subscriber.onCompleted();
                } catch (Exception e) {
                    System.out.println(e);
                }

            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<InputStream>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(InputStream is) {
                updateUI();
            }
        });
    }

    private void getXmlData(InputStream is) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new InputStreamReader(is, "UTF-8"));
            String tag;
            xpp.next();
            int eventType = xpp.getEventType();

            data = new DataGetterSetters();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기

                        if (data.getMainItem() == null) {
                            if (tag.equalsIgnoreCase("title")) {
                                xpp.next();
                                data.setTitle(xpp.getText());
                            } else if (tag.equalsIgnoreCase("source")) {
                                xpp.next();
                                data.setSource(xpp.getText());
                            } else if (tag.equalsIgnoreCase("description")) {
                                xpp.next();
                                data.setDescription(xpp.getText());
                            } else if (tag.equals("item")) {
                                xpp.next();
                                data.setMainItem(new MainItem());
                            }
                        } else {
                            if (tag.equalsIgnoreCase("title")) {
                                xpp.next();
                                data.getMainItem().setTitle(xpp.getText());
                            } else if (tag.equalsIgnoreCase("category")) {
                                xpp.next();
                                data.getMainItem().setCategory(xpp.getText());
                            } else if (tag.equalsIgnoreCase("forecastIssue")) {
                                data.getMainItem().setForecastIssue(xpp.getText());
                            } else if (tag.equalsIgnoreCase("validTime")) {
                                xpp.next();
                                data.getMainItem().setValidTime(xpp.getText());
                            } else if (tag.equalsIgnoreCase("weatherForecast")) {
                                data.getMainItem().setWeatherForecast(new ArrayList<WeatherForecast>());
                            }
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag = xpp.getName();    //테그 이름 얻어오기
                        if (tag.equals("forecastIssue")) {
                            String date = xpp.getAttributeValue(null, "date");
                            String time = xpp.getAttributeValue(null, "time");
                            data.getMainItem().setForecastIssue(date + " " + time);
                        } else if (tag.equals("area")) {
                            WeatherForecast weatherForecast = new WeatherForecast();
                            weatherForecast.setForecast(xpp.getAttributeValue(null, "forecast"));
                            weatherForecast.setLat(xpp.getAttributeValue(null, "lat"));
                            weatherForecast.setLon(xpp.getAttributeValue(null, "lon"));
                            weatherForecast.setName(xpp.getAttributeValue(null, "name"));
                            data.getMainItem().getWeatherForecast().add(weatherForecast);
                        }
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void updateUI(){
        txt_title.get().setText(data.getTitle());
        txt_description.get().setText(data.getDescription());
        txt_item_title.get().setText(data.getMainItem().getTitle());
        txt_item_category.get().setText(data.getMainItem().getCategory());
        txt_item_forecastissue.get().setText(data.getMainItem().getForecastIssue());
        txt_item_validtime.get().setText(data.getMainItem().getValidTime());
        adapter.setData(getApplicationContext(), data.getMainItem().getWeatherForecast());
        adapter.notifyDataSetChanged();
    }


}