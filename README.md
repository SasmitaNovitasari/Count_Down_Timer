## count-down-timer

Timer count down extends from TextView, this library also have timer with ProgressBar style

## Installation

```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

```
	dependencies {
	        implementation 'com.github.SasmitaNovitasari:count-down-timer:input_latest_version'
	}
```

## XML Feature
| XML               | Detail                                                                                                                                                                                                                            | Value Allowed |
|-------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------|
| zero_number       | You can choose to show 0 time or not. Ex, <br/><br/>  [0 day 1 hour 0 minute 0 second (true)] <br/> [1 hour 0 minute 0 second (false)] <br/><br/>  The first 0 in day not showing if you set to false <br/> Default value is true | boolean       |
| start_date        | Default format dd MM yyyy HH:mm:ss <br/> Default is current date [optional]                                                                                                                                                       | String        |
| end_date          | Default format dd MM yyyy HH:mm:ss [must]                                                                                                                                                                                         | String        |
| start_date_format | Set date format. This use in SimpleDateFormat() [optional]                                                                                                                                                                        | String        |
| end_date_format   | Set date format. This use in SimpleDateFormat() [optional]                                                                                                                                                                        | String        |
| finish_text       | This text will showing when counter finish <br/> Default value "Complete!" [optional]                                                                                                                                             | String        |
| label_day         | Default value : Days [optional]                                                                                                                                                                                                   | String        |
| label_hour        | Default value : Hours [optional]                                                                                                                                                                                                  | String        |
| label_minute      | Default_value : Minutes [optional]                                                                                                                                                                                                | String        |
| label_second      | Default value : Seconds [optional]                                                                                                                                                                                                | String        |


## Example XML
```xml
    <com.sasmitanovitasari.countdowntimer.Tick
        android:id="@+id/countdown"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:end_date="19 07 2022 9:00:00"
        app:start_date="18 07 2022 8:00:00"
        app:zero_number="true" />
```

```xml
    <com.sasmitanovitasari.countdowntimer.HorizontalBar
        style="@android:style/Widget.ProgressBar.Horizontal"
        android:layout_width="200dp"
        android:layout_height="10dp"
        android:id="@+id/progress"
        app:end_date="19 07 2022 9:00:00"
        app:start_date="19 07 2022 8:59:30"/>
```
