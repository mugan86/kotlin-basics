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
    fun getKilometersPerHourToPaceMinKm(speed_km_h: Double): String {
        //Get pace minutes and seconds
        var minScKm:Double = 60 / speed_km_h;

        //Convert String
        var result_str:String = minScKm.toString();

        //Get decimal value
        var index: Int = result_str.indexOf(".");

        //Get only pace minutes

        var min_pace: Int = parseInt(minScKm.toString());
        if (index != -1)  min_pace = this.removeDecimalValue(minScKm);

        //Get only pace seconds
        var sec_pace: Int = 0;
        if (index != -1) sec_pace = this.RemoveDecimalValue((parseDouble("0" + result_str.substring(index)))*60);

        return this.getPaceMinKMInCorrectFormat(min_pace.toString(), sec_pace.toString());
    }

    /**
     *
     * @param min_pace int value (minutes)
     * @param sec_pace int value (seconds)
     * @return String, result example '04:17 min/km = 14.007782 km/h'
     */
    fun paceMinKmToKilometersPerHour(min_pace: Int, sec_pace:Int): String {
        /*if (sec_pace >= 60) return String.
                format("Seconds only 0-59 range (Not correct data input: %d minutes - %d seconds", min_pace, sec_pace);*/

        //Obtain total time with decimals (min and seconds)
        val total_time = (sec_pace / 60) + min_pace;

        val km_h: Double  = (60 / total_time).toDouble()

        return ((Math.round(km_h * 100))/100).toString()
    }

    /**
     * @param speed_m_min meters for min
     * @return String, result example '241.9 m/min = 14.514 km/h'
     */
    fun metersMinuteToKilometersPerHour(speed_m_min: Double): Double {
        if (speed_m_min <= 0) return 0.0

        return this.getDoubleValue(((speed_m_min*60) / 1000), 2)
    }

    /**
     * @param speed_km_h Add value in kilometers / hour. For Example: 14.5
     * @return String, result example '14.514 km/h = 241.9 m/min'
     */
    fun kilometersPerHourToMetersMinute(speed_km_h: Double): Double {
        if (speed_km_h <= 0) return 0.0
        return this.getDoubleValue((((speed_km_h/60)*1000)), 2)
    }

    /**
     * @param speed_m_sec meters for second
     * @return String, result example '4.0316 m/sec = 14.514 km/h'
     */
    fun metersSecondToKilometersPerHour(speed_m_sec: Double) : Double {

        return this.metersMinuteToKilometersPerHour(speed_m_sec * 60)
    }

    /**
     * @param speed_km_h Add value in kilometers / hour. For Example: 14.5
     * @return String, result example '14.514 km/h = 4.0316 m/sec '
     */
    fun kilometersPerHourToMetersSecond(speed_km_h: Double) : Double {
        val result_with_m_min = this.kilometersPerHourToMetersMinute(speed_km_h)
        //TODO Find to replace in strings kotlin
        return this.getDoubleValue(((parseDouble(result_with_m_min.replace(",", ".").trim())) / 60), 2)
    }

    /**
     * @param time HH:MM:SS formatAdd value in kilometers / hour. For Example: 01:00:00
     * @param km   double value to asign total kms to convert. For Example: 14.0
     * @return String with pace min/km, result example '01:00:00 / 15km = 04:00min/km'
     */
    fun timeAndKilometersToPacePerKm(time: String, km: Double): String {

        //Get total time to complete km in seconds
        var time_complete_km_in_seconds = this.getTimeInSecondsFromTime(time)/km;
        //Apply rint
        time_complete_km_in_seconds = Math.round(time_complete_km_in_seconds).toDouble()

        //Get min
        val min_pace: Int = (time_complete_km_in_seconds / 60).toInt()

        //Get seconds to pace per km
        val sec_pace: Int = (time_complete_km_in_seconds % 60).toInt();

        //Return with pretty format
        return this.getPaceMinKMInCorrectFormat(min_pace.toString(), sec_pace.toString());
    }

    /**
     * @param time        HH:MM:SS formatAdd value in kilometers / hour. For Example: 01:00:00
     * @param pace_min_km MM:SS String value with pace per km. For Example: 04:00 min/km
     * @return String with pace min/km, result example '01:00:00 / 04:00min/km = 15km'
     */
    fun timeAndPacePerKmToTotalKilometers(time: String, pace_min_km:String): Double {

        //Denbora totala segundutan lortzeko
        val sgTotalak = this.getTimeInSecondsFromTime(time)

        //Get one km complete in seconds
        val sgKm: Int =this.getTimeInSecondsFromPacePerKm(pace_min_km)

        //Denbora zehatz batean egin ditugun km kopurua emango da
        val kilometers: Double = (sgTotalak/sgKm).toDouble()

        return this.getDoubleValue((Math.round(kilometers*1000)/1000).toDouble(), 2)
    }

    /**
     * @param km          double value to asign total kms to convert. For Example: 14.0
     * @param pace_min_km MM:SS String value with pace per km. For Example: 04:00 min/km
     * @return String with pace min/km, result example '15km / 04:00min/km = 01:00:00'
     */
    fun totalKilometersAndPacePerKmToTime(km: Double, pace_min_km: String): String {

        //total seconds to complete one kilometer (from pace per km)
        val sgKm = this.getTimeInSecondsFromPacePerKm(pace_min_km)

        //Total time to complete x km in x min per km
        val total_time_in_seconds = sgKm * km

        //Convert total seconds in time format
        val hours = (total_time_in_seconds / 3600).toString()
        val minutes = ((total_time_in_seconds % 3600) / 60).toString()
        val seconds = (total_time_in_seconds % 60).toString()
        return this.getWithTwoDigits(hours) + ":" +
                this.getWithTwoDigits(minutes) + ":"+
                this.getWithTwoDigits(seconds);
    }

    /**
     * @param time        HH:MM:SS formatAdd value For Example: 01:00:00
     * @param total_steps int value. For Example: 12304.
     * @return String with pace min/km, result example 'x step/min'
     */
    fun stepsPerMinuteFromTotalStepsAndTime(time: String, total_steps: Int): Int {
        /******
         * 14500 steps in 1h18min00sg (4680seconds)
         * x steps in minute (60 seconds)
         *
         * x = (14500 * 60) / 4680 = 185,89 step / min
         */
        return 14500 * 60 /this.getTimeInSecondsFromTime(time)
    }

    /**
     * @param km          double value to asign total kms to convert. For Example: 14.0
     * @param total_steps int value. For Example: 12304.
     * @return String with pace min/km, result example '15km / 04:00min/km = 01:00:00'
     */
    fun stepsPerKmFromTotalStepsAndDistanceKm(km: Double, total_steps: Int): String {
        return ((total_steps) / km).toString();
    }

    /**
     * @param distance double value to asign total kms to convert. For Example: If value > 5 considerer input meters
     * @return String with vO2max, result example '3850 (metres)-> VO2 max = 74 To calculate: (meters - 504) / 45
     */
    fun vO2MaxInCooperTest(_distance: Double): Double {
        var distance = _distance
        if (distance < 1000) /*Distance in kmeters*/ {
            distance = parseDouble(this.getDistanceinMeters(distance.toString()))
        }

        return distance //this.(((distance - 504) / 45).toString(), 3);
    }

    /**
     * @param v02   double value to asign vO2max that use to calculate distance to complete to obtain this vO2 max.
     *              For Example: 74 (VO2max) = 3850 m in 12 minutes.
     * @param in_km To return value in kilometers instead of meters (default)
     * @return String with distance in meters or km (boolean specific)
     */
    fun distanceNeedToObtainSpecificVO2MaxWithCooperTest(v02:Double, in_km: Boolean): String{
        if (!in_km) return ((v02*45) + 504).toString();
        return (this.getDoubleValue(parseDouble(this.getDistanceInKms
                                (((v02*45) + 504).toString())), 3)).toString();
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

        val zone = "Zone ${((percent - 50) / 10 + 1)}: ";
        var low_fc = parseInt(low_fc);
        var max_fc = parseInt(max_fc);
        return "${zone} ${(((max_fc-low_fc) * (percent)/100) + low_fc)} - ${(((max_fc-low_fc) * (percent+10) / 100) + low_fc)}";
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
    fun getClimbPercentage(_distance: Double, Climb: Double, distType: Int): Double
    {
        var distance = _distance
        if (distType != 1) //Not metres
        {
            if (distType == 2) distance = parseDouble(this.getDistanceinMeters(distance.toString()))
        }
        return this.getDoubleValue(Climb * 100 / distance, 2);
    }

    /**
     * Get Climb Meters per km
     * @param distance: Distance total in select format (specific in distType)
     * @param Climb: Climb Metres total (or descent, pass "-" value)
     * @param distType: Distance unit (m, mile, km,...)
     * @return Climb m+ per km
     */
    fun getClimbMetersPerKm(_distance:Double, Climb: Int, distType: Int): Double
    {
        var distance = _distance
        if (distType != 2) //Not metres
        {
            if (distType == 1) distance = parseDouble(this.getDistanceInKms(distance.toString()))
        }
        return this.getDoubleValue(Climb / distance, 2);
    }

    /**
     * Round value with specific decimals
     * @param pace_per_km: Time total in seconds to complete one kilometer
     * @param pace_per_km: Time total in seconds to complete one kilometer
     * @return Return result with select digit total. For example result 182.3453 with digit = 2 => 182.34
     */
    private fun getDoubleValue(value: Double,digit: Int): Double {
        var  number:Double;
        if(value==null) number= 0.0;
        else number = value;
        return (Math.round(number * 100) / 100).toDouble()//.toFixed(digit);
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

    private fun removeDecimalValue(value: Double): Int
    {
        var index: Int = value.toString().indexOf(".");

        if (index == -1) return parseInt(value.toString())
        return parseInt((value.toString()).substring(0, index));
    }
}