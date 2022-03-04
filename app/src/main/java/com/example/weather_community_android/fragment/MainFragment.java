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
import com.example.weather_community_android.model.Temp;
import com.example.weather_community_android.model.TempClass;
import com.example.weather_community_android.model.TempItem;
import com.example.weather_community_android.model.Weather;
import com.example.weather_community_android.model.WeatherClass;
import com.example.weather_community_android.model.WeatherItem;
import com.example.weather_community_android.network.ApiObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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
    private TextView maxTemp;
    private TextView minTemp;

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
        maxTemp = view.findViewById(R.id.tv_max_temp);
        minTemp = view.findViewById(R.id.tv_min_temp);
        getWeather(nx,ny);
//        getTemp(nx, ny);
        return view;
    }

    private void getWeather(String nx, String ny) {

        Calendar cal = Calendar.getInstance();
        baseDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        String timeH = new SimpleDateFormat("HH", Locale.getDefault()).format(cal.getTime());
        String timeM = new SimpleDateFormat("HH", Locale.getDefault()).format(cal.getTime());
        final Weather weather = new Weather();
        baseTime = getBaseTime(timeH, timeM);

        if (timeH == "00" && baseTime == "2330") {
            cal.add(Calendar.DATE, -1);
            baseDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        }
        ApiObject.INSTANCE.getRetrofitService().GetWeather(60, 1, "JSON", baseDate, baseTime, nx, ny)
        .enqueue(new Callback<WeatherClass>() {
            @Override
            public void onResponse(Call<WeatherClass> call, Response<WeatherClass> response) {

                if (response.isSuccessful()) {
                    List<WeatherItem> items = new ArrayList<>();

                    items = response.body().getResponse().getBody().getItems().getItem();
                    System.out.println(items + "items!!!!!!!!!!!!1");
                    for (int i = 0; i < items.size(); i++) {
                        if(items.get(i).getCategory().equals("PTY")) {
                            weather.setRainType(items.get(i).getFcstValue());
                        }
                        if(items.get(i).getCategory().equals("REH"))
                            weather.setHumidity(items.get(i).getFcstValue());
                        if(items.get(i).getCategory().equals("SKY"))
                            weather.setSky(items.get(i).getFcstValue());
                        if(items.get(i).getCategory().equals("T1H"))
                            weather.setTemp(items.get(i).getFcstValue());
                        else
                            continue;
                    }
                    setWeather(weather);
                }


            }

            @Override
            public void onFailure(Call<WeatherClass> call, Throwable t) {
                Log.d("api fail", t.getMessage().toString());
                System.out.println(t.getMessage() + "api fail!!!!!!!!");
            }
        });

    }

    private void getTemp(String nx, String ny) {
        Calendar cal = Calendar.getInstance();
        baseDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        String timeH = new SimpleDateFormat("HH", Locale.getDefault()).format(cal.getTime());
        final Temp temp = new Temp();
        baseTime = getTime(timeH);
        if (Integer.valueOf(baseTime) >= Integer.valueOf("2000")) {
            cal.add(Calendar.DATE, -1);
            baseDate = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(cal.getTime());
        }
        ApiObject.INSTANCE.getRetrofitService().GetTemp(10, 1, "JSON", baseDate, baseTime, nx, ny)
                .enqueue(new Callback<TempClass>() {
                    @Override
                    public void onResponse(Call<TempClass> call, Response<TempClass> response) {
                        Toast.makeText(getContext(), "함수 작동", Toast.LENGTH_SHORT).show();
                        if (response.isSuccessful()) {
                            List<TempItem> tempItems = new ArrayList<>();
                            tempItems = response.body().getResponse().getBody().getItems().getItem();
                            Log.e("api test", tempItems.toString());
                            for (int i = 0; i < tempItems.size(); i++) {
                                if(tempItems.get(i).getCategory().equals("POP")) {
                                    temp.setRainRation(tempItems.get(i).getFcstValue());
                                }
                                if(tempItems.get(i).getCategory().equals("TMX"))
                                    temp.setMaxTemp(tempItems.get(i).getFcstValue());
                                if(tempItems.get(i).getCategory().equals("TMN"))
                                    temp.setMinTemp(tempItems.get(i).getFcstValue());
                                else
                                    continue;
                            }
                        }
                        setTemp(temp);


                    }

                    @Override
                    public void onFailure(Call<TempClass> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(t.getMessage());
                        Log.d("api fail", t.getMessage().toString());
                    }
                });
    }
    private void setWeather(Weather weatherValue) {
        String result = "";
        System.out.println(weatherValue.getRainType() + "rainType");
        switch (weatherValue.getRainType()) {
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
        switch (weatherValue.getSky()) {
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
        humidity.setText(weatherValue.getHumidity() + "%");
        sky.setText(skyResult);
        temp.setText(weatherValue.getTemp());

    }

    private void setTemp(Temp tempValue) {
        rainRatio.setText(tempValue.getRainRation());
        maxTemp.setText(tempValue.getMaxTemp());
        minTemp.setText(tempValue.getMinTemp());
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

    private String getTime(String h) {
        String result = "";
        if (h.equals("00") || h.equals("02")||h.equals(03)) {
            result = "2000";
        } else if (h.equals("03") || h.equals("04") || h.equals("05")) {
            result = "2300";
        } else if (h.equals("06")||h.equals("07")||h.equals("08")) {
            result = "0200";
        } else if (h.equals("09") || h.equals("10") || h.equals("11")) {
            result = "0500";
        } else if (h.equals("12")||h.equals("13")||h.equals("14")) {
            result = " 0800";
        } else if (h.equals("15") || h.equals("16") || h.equals("17")) {
            result = "1100";
        } else if (h.equals("18") || h.equals("19") || h.equals("20")) {
            result = "1400";
        } else {
            result = "1700";
        }
        return result;
    }
}