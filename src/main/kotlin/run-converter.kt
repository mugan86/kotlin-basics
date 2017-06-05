import com.sun.xml.internal.fastinfoset.util.StringArray
import java.lang.Double.parseDouble
import java.lang.Float.parseFloat
import java.lang.Integer.parseInt

/**
 * Created by amugica on 31/05/2017.
 */

/**
 *
        **/

interface RunConverterIF {
    fun getKilometersPerHourToPaceMinKm(speed_km_h: Double) {
        //Get pace minutes and seconds
        var minScKm:Double = 60 / speed_km_h;

        //Convert String
        var result_str:String = minScKm.toString();

        //Get decimal value
        var index: Int = result_str.indexOf(".");

        //Get only pace minutes

        var min_pace: Double = minScKm;
        if (index != -1)  min_pace = this.RemoveDecimalValue(minScKm);

        //Get only pace seconds
        var sec_pace: Int = 0;
        if (index != -1) sec_pace = this.RemoveDecimalValue((parseFloat("0" + result_str.substring(index)))*60);

        return this.GetPaceMinKMInCorrectFormat(min_pace, sec_pace);
    }

    /**
     * @param distance double value to asign total kms to convert. For Example: If value > 5 considerer input meters
     * @return String with vO2max, result example '3850 (metres)-> VO2 max = 74 To calculate: (meters - 504) / 45
     */
    fun vO2MaxInCooperTest(distance: Double) {

        if (distance < 1000) /*Distance in kmeters*/ {
            distance = this.GetDistanceinMeters(distance)
        }

        return this.getDoubleValue(((distance - 504) / 45).toString(), 3);
    }

    /**
     * @param v02   double value to asign vO2max that use to calculate distance to complete to obtain this vO2 max.
     *              For Example: 74 (VO2max) = 3850 m in 12 minutes.
     * @param in_km To return value in kilometers instead of meters (default)
     * @return String with distance in meters or km (boolean specific)
     */
    fun distanceNeedToObtainSpecificVO2MaxWithCooperTest(v02:Double, in_km: Boolean): String{
        if (!in_km) return ((v02*45) + 504).toString();
        return (this.GetDoubleValue((this.getDistanceInKms((v02*45) + 504)).toString(), 3));
    }

    /**
     * @param percent : Percent to calculate FC range min value to FC zone (for example 50 = Z1)
     *                Z1: 50-60
     *                Z2: 60-70
     *                Z3: 70-80
     *                Z4: 80-90
     *                Z5: 90-100
     * @param low_fc  min ppm
     * @param max_fc  max ppm
     * @return Obtain select percent zone ppm range
     */
    fun obtainFCZoneWithPercent(percent:Int, low_fc: String, max_fc: String): String {

        val zone = "Zone " + ((percent - 50) / 10 + 1) + ": ";
        var low_fc = parseInt(low_fc);
        var max_fc = parseInt(max_fc);
        return zone + (((max_fc-low_fc) * (percent)/100) + low_fc) + " - " + (((max_fc-low_fc) * (percent+10) / 100) + low_fc);
    }

    /**
     * @param low_fc min ppm
     * @param max_fc max ppm
     * @return FC zones with PPM range
     */
    fun obtainResumeOfFCZones(low_fc: String, max_fc: String): Array<String?> {
        val low_fc = parseInt(low_fc);
        val max_fc = parseInt(max_fc);
        var fc_data = arrayOfNulls<String>(4)
        var pos = 0;
        for (i in 50..90 step 10)
        //for (i = 50; i <= 90; i = i+10)
        {
            fc_data[pos] = this.obtainFCZoneWithPercent(i, low_fc.toString(), max_fc.toString())
            pos++
        }
        return fc_data;
    }

    /**
     * @param value to remove decimals (if exist)
     * @return Int value
     */
    fun RemoveDecimalValue(value: Double): Int
    {
        val valueString : String = value.toString();
        var index: Int = valueString.indexOf(".");

        if (index == -1) return parseInt(valueString);
        return parseInt(valueString.substring(0, index));
    }

    /**
     * @param feets Feets to convert to metres
     * @return Meters
     */
    fun convertFeetsToMeters(feets: String): String{
        return (parseFloat(feets)/3.28084).toString();
    }

    /**
     * @param meters meters to convert to feets
     * @return Feets
     */
    fun convertMetersToFeets(meters: String): String {
        return (parseFloat(meters)*3.28084).toString();
    }

    /**
     * @param miles Miles to convert to metres
     * @return Meters
     */
    fun convertMilesToMeters(miles: String): String {
        return (parseFloat(miles)/0.000621371).toString();
    }

    /**
     * @param meters meters to convert to miles
     * @return Miles
     */
    fun convertMetersToMiles(meters: String): String {
        return (parseFloat(meters)*0.000621371).toString();
    }

    /**
     * @param yards Yards to convert to metres
     * @return Meters
     */
    fun convertYardsToMeters(yards: String): String {
        return (parseFloat(yards) /1.0936133333333).toString();
    }

    /**
     * @param meters Meters to convert to yards
     * @return Yards
     */
    fun convertMetersToYards(meters: String) : String {
        return (parseFloat(meters)*1.0936133333333).toString();
    }


    /**
     * @param sec_pace Pace per km seconds value
     * @param min_pace Pace per km minutes value
     * @return Correct format to show pace per km, f.e: 04:07
     */
    fun getPaceMinKMInCorrectFormat(min_pace:String, sec_pace: String): String {
        var sec_pace_ = parseInt(sec_pace);
        var min_pace_ = parseInt(min_pace);
        //Add format to result depending minutes and seconds pace
        if (sec_pace_<10 && min_pace_ < 10) return "0" + min_pace_ + ":0" + sec_pace_;
        else if (sec_pace_>=10 && min_pace_<10) return "0" + min_pace + ":" +sec_pace;
        else if (sec_pace_<10 && min_pace_>=10) return min_pace + ":0" +sec_pace;
        else return min_pace + ":" +sec_pace;
    }

    /**
     * @param time: Time total in seconds, use to convert to time HH:MM:SS format
     * @return Return total seconds in time format. 3600seconds = 01:00:00
     */
    fun getTimeInSecondsFromTime(time:String): Int {

        var parts = time.split(":");
        var hour = parts[0]; // 004
        var min = parts[1]; // 034556
        var sec = parts[2]; // 034556

        //Total time (in seconds) = (3600*hour) + (60*min) + sec
        return (3600 * parseInt(hour)) + (60 * parseInt(min)) + parseInt(sec);
    }

    /**
     * @param pace_per_km: Time total in seconds to complete one kilometer
     * @return Return total seconds to use in one km in min/km format. 240 seconds = 04:00
     */
    fun getTimeInSecondsFromPacePerKm(pace_per_km:String): Int
    {
        println(pace_per_km);
        var parts = pace_per_km.split(":");
        var min = parts[0]; // 034556
        var sec = parts[1]; // 034556

        println("MIN: " + min + " / SEC: " + sec);

        //Total time (in seconds) = (3600*hour) + (60*min) + sec
        return (60 * parseInt(min)) + parseInt(sec);
    }

    /**
     * Get Climb Percentage (%)
     * @param distance: Distance in select format (specific in distType)
     * @param Climb: Climb Metres (or descent, pass "-" value)
     * @param distType: Distance unit (m, mile, km,...)
     * @return Climb / Descent percentage
     * vertical distance (m) · 100/horizontal distance = climb%
     */
    fun getClimbPercentage(distance: Float, Climb: Float, distType: Int): Double
    {
        if (distType != 1) //Not metres
        {
            if (distType == 2) distance = this.GetDistanceinMeters(distance);
        }
        return this.GetDoubleValue(Climb * 100 / distance, 2);
    }

    /**
     * Get Climb Meters per km
     * @param distance: Distance total in select format (specific in distType)
     * @param Climb: Climb Metres total (or descent, pass "-" value)
     * @param distType: Distance unit (m, mile, km,...)
     * @return Climb m+ per km
     */
    fun getClimbMetersPerKm(distance:Float, Climb: Int, distType: Int): Float
    {
        if (distType != 2) //Not metres
        {
            if (distType == 1) distance = this.GetDistanceInKms(distance);
        }
        return this.GetDoubleValue(Climb / distance, 2);
    }

    /**
     * Round value with specific decimals
     * @param pace_per_km: Time total in seconds to complete one kilometer
     * @param pace_per_km: Time total in seconds to complete one kilometer
     * @return Return result with select digit total. For example result 182.3453 with digit = 2 => 182.34
     */
    private fun GetDoubleValue(value: Float,digit: Int): Float{
        var  number:Float;
        if(value==null) number= 0.0F;
        else number = value;
        return (Math.round(number * 100) / 100).toFixed(digit);
    }

    /**
     * Convert distance in kms to meters
     * @param distance: Distance in kilometers
     * @return Distance in meters. 1km = 1000m
     */
    private fun getDistanceinMeters(distance: String): String {
        return (parseFloat(distance) * 1000).toString();
    }

    /**
     * Convert distance in meters from kms
     * @param distance: Distance in metres
     * @return Distance in kms. 1000m = 1km
     */
    private fun getDistanceInKms(meters:String): String {
        return (parseFloat(meters) / 1000).toString();
    }


    /**
     * Convert distance in kms to meters
     * @param distance: Distance in kilometers
     * @return Distance in meters. 1Mile = 1609.3399metres
     */
    private fun getDistanceMilesinMeters(distance: String): String
    {
        return (parseFloat(distance) * 1609.3399).toString();
    }

    /**
     * Convert distance in miles from mts
     * @param distance: Distance in metres
     * @return Distance in kms. 1609.3399 metres = 1mile
     */
    private fun getDistanceInMiles(meters: String): String
    {
        return (parseFloat(meters) / 1609.3399).toString();
    }


    /**
     *
     * @param number: Number to use to return with two digits
     * @return Value in string format with 2 chars length. 9 = "09"
     */
    private fun getWithTwoDigits(value: String): String {
        //Remove all decimals before than asign correct format
        var value = this.RemoveDecimalValue(parseDouble(value));
        if (value < 10) return "0"+value;
        return (value).toString();
    }
}