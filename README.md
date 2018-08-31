## :warning: No longer maintained :warning:  
---  
# StateButton
*Android button suitable for network operations*  
![Sample](/img/sample.gif)  
### Installation
Add the following lines to your `build.gradle`
```gradle
repositories {
   maven {
       url 'https://dl.bintray.com/andrea-rosa/maven/'
   }
}
 
dependencies {
     compile 'org.altervista.andrearosa:statebutton:1.0.0'
}
```
### Usage
Put in your `layout.xml`:
```xml
<org.altervista.andrearosa.statebutton.StateButton
   android:id="@+id/mButton"
   app:state="enabled"
   ...
   />
```
Then, in your Activity/Fragment:
```java
...
public class MainActivity extends AppCompatActivity {
    private StateButton mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (StateButton) findViewById(R.id.mButton);
        
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check if you can click on the button
                if(mButton.getState() == StateButton.BUTTON_STATES.ENABLED) {
                    // set it to loading state
                    mButton.setState(StateButton.BUTTON_STATES.LOADING);
                    // do stuff
                    if(/*get some error*/) {
                        // show the failed state
                        mButton.setState(StateButton.BUTTON_STATES.FAILED);
                    } else {
                        // show the success state
                        mButton.setState(StateButton.BUTTON_STATES.SUCCESS);
                    }
                    // than you can set the button to the original state
                    mButton.setState(StateButton.BUTTON_STATES.ENABLED);
                }
            }
        });
        
    }
...
}
```

### Configuration
You can define the following attributes:
 - `state` the initial state of the button (*enum*)
   * `disabled`
   * `enabled`
   * `loading`
   * `success`
   * `failed`
 - `disabledText` text to display when the button is disabled
 - `enabledText` text to display when the button is enabled
 - `loadingText` text to display when the button is loading
 - `successText` text to display when the button is in success state
 - `failedText` text to display when the button is in failed state
 - `disabledTextColor` color of disabled text (*resource id*)
 - `enabledTextColor` color of enabled text (*resource id*)
 - `loadingTextColor` color of loading text (*resource id*)
 - `successTextColor` color of success text (*resource id*)
 - `failedTextColor` color of failed text (*resource id*)
 - `disabledBackground` background for disabled state
 - `enabledBackground` background for enabled state
 - `loadingBackground` background for loading state
 - `successBackground` background for success state
 - `failedBackground` background for failed state
 - `disabledIcon` icon for disabled state
 - `enabledIcon` icon for enabled state
 - `loadingIcon` icon for loading state
 - `successIcon` icon for success state
 - `failedIcon` icon for failed state
 - `disabledIconColor` icon color for disabled state
 - `enabledIconColor` icon color for enabled state
 - `loadingIconColor` icon color for loading state
 - `successIconColor` icon color for success state
 - `failedIconColor` icon color for failed state
 - `disabledIconVisible` icon visibility for disabled state (*boolean*)
 - `enabledIconVisible` icon visibility for enabled state (*boolean*)
 - `loadingIconVisible` icon visibility for loading state (*boolean*)
 - `successIconVisible` icon visibility for success state (*boolean*)
 - `failedIconVisible` icon visibility for failed state (*boolean*)

**To much settings?**  
Don't worry! There are 19 predefined styles that you can use.  
Just add `style` attribute in your xml and the texts for states:
```xml
<org.altervista.andrearosa.statebutton.StateButton
   style="@style/StateButton_md_redStyle"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   app:disabledText="Disbled"
   app:enabledText="Click me!"
   app:failedText="Failed"
   app:loadingText="Loading"
   app:successText="Success"
   app:state="enabled" />
```

Predefined styles are:
 - `StateButton_md_redStyle`
 - `StateButton_md_blueStyle`
 - `StateButton_md_pinkStyle`
 - `StateButton_md_purpleStyle`
 - `StateButton_md_deeppurpleStyle`
 - `StateButton_md_indigoStyle`
 - `StateButton_md_lightblueStyle`
 - `StateButton_md_cyanStyle`
 - `StateButton_md_tealStyle`
 - `StateButton_md_greenStyle`
 - `StateButton_md_lightgreenStyle`
 - `StateButton_md_limeStyle`
 - `StateButton_md_yellowStyle`
 - `StateButton_md_amberStyle`
 - `StateButton_md_orangeStyle`
 - `StateButton_md_deeporangeStyle`
 - `StateButton_md_brownStyle`
 - `StateButton_md_greyStyle`
 - `StateButton_md_bluegreyStyle`

