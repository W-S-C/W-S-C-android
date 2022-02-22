package com.example.weather_community_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weather_community_android.R;
import com.example.weather_community_android.model.WeatherDAO;
import com.example.weather_community_android.network.ApiObject;
import com.example.weather_community_android.network.ITEM;
import com.example.weather_community_android.network.ITEMS;
import com.example.weather_community_android.network.WEATHER;
import com.example.weather_community_android.network.WeatherApiService;
import com.example.weather_community_android.network.WeatherApiServiceKt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import io.reactivex.observers.TestObserver;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String baseDate = "20220221";
    private String baseTime = "0630";
    private String nx = "55";
    private String ny = "127";

    private TextView rainRatio;
    private TextView rainType;
    private TextView humidity;
    private TextView sky;
    private TextView temp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        rainRatio = view.findViewById(R.id.tv_rain_ratio);
        rainType = view.findViewById(R.id.tv_rain_type);
        humidity = view.findViewById(R.id.tv_humidity);
        sky = view.findViewById(R.id.tv_sky);
        temp = view.findViewById(R.id.tv_temp);
        getWeather(nx,ny);
        return view;
    }

    private void getWeather(String nx, String ny) {

        Calendar cal = Calendar.getInstance();
        baseDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        String timeH = new SimpleDateFormat("HH", Locale.getDefault()).format(cal.getTime());
        String timeM = new SimpleDateFormat("HH", Locale.getDefault()).format(cal.getTime());
        final WeatherDAO weatherDAO = new WeatherDAO();
        baseTime = getBaseTime(timeH, timeM);

        if (timeH == "00" && baseTime == "2330") {
            cal.add(Calendar.DATE, -1);
            baseDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        }
        Toast.makeText(getContext(), "함수는 작동됨", Toast.LENGTH_LONG ).show();
        ApiObject.INSTANCE.getRetrofitService().GetWeather(60, 1, "JSON", baseDate, baseTime, nx, ny)
        .enqueue(new Callback<WEATHER>() {
            @Override
            public void onResponse(Call<WEATHER> call, Response<WEATHER> response) {
                Toast.makeText(getContext(), "가져왔음", Toast.LENGTH_LONG ).show();

                if (response.isSuccessful()) {
                    List<ITEM> items = new ArrayList<>();

                    items = response.body().getResponse().getBody().getItems().getItem();
                    System.out.println(items + "items!!!!!!!!!!!!1");
                    for (int i = 0; i < items.size(); i++) {
                        if(items.get(i).getCategory().equals("PTY")) {

                            System.out.println(items.get(i).getFcstValue() + "items.getFctValue");
                            weatherDAO.setRainType(items.get(i).getFcstValue());
                        }
                        if(items.get(i).getCategory().equals("REH"))
                            weatherDAO.setHumidity(items.get(i).getFcstValue());
                        if(items.get(i).getCategory().equals("SKY"))
                            weatherDAO.setSky(items.get(i).getFcstValue());
                        if(items.get(i).getCategory().equals("T1H"))
                            weatherDAO.setTemp(items.get(i).getFcstValue());
                        else
                            continue;
                    }
                    setWeather(weatherDAO);
                }


            }

            @Override
            public void onFailure(Call<WEATHER> call, Throwable t) {
                Log.d("api fail", t.getMessage().toString());
                System.out.println(t.getMessage() + "api fail!!!!!!!!");
            }
        });

    }

    private void setWeather(WeatherDAO weatherDAO) {
        String result = "";
        System.out.println(weatherDAO.getRainType() + "rainType");
        switch (weatherDAO.getRainType()) {
            case "0":
                result = "없음";
                break;
            case "1":
                result = "비";
                break;
            case "2":
                result = "비/눈";
                break;
            case "3":
                result = "눈";
                break;
            case "4":
                result = "소나기";
                break;
            case "5":
                result = "빗방울";
                break;
            case "6":
                result = "빗방울/눈날림";
                break;
            case "7":
                result = "눈날림";
                break;
            default:
                result = "오류";
                break;
        }
        String skyResult = "";
        switch (weatherDAO.getSky()) {
            case "1":
                skyResult = "맑음";
                break;
            case "3":
                skyResult = "구름 맑음";
                break;
            case "4":
                skyResult = "흐림";
                break;
            default:
                skyResult = "오류";
                break;
        }
        rainType.setText(result);
        humidity.setText(weatherDAO.getHumidity() + "%");
        sky.setText(skyResult);
        temp.setText(weatherDAO.getTemp());

    }
    private String getBaseTime(String h , String m) {

        String result = "";
        if (Integer.valueOf(m) < 45) {
            if (h == "00") {
                result = "2330";
            } else {
                int resultH = Integer.valueOf(h) - 1;
                if (resultH < 10) {
                    result = "0" + resultH + "30";
                } else {
                    result = String.valueOf(resultH) + "30";
                }
            }
        } else {
            result = h + "30";
        }
        return result;

    }
}