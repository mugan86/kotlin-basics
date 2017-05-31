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
        if (index != -1)  min_pace = this.RemoveDecimalValue(min_sc_km);

        //Get only pace seconds
        let sec_pace: number = 0;
        if (index != -1) sec_pace = this.RemoveDecimalValue((parseFloat("0" + result_str.substring(index)))*60);

        return this.GetPaceMinKMInCorrectFormat(min_pace, sec_pace);
    }

    /**
     * @param value to remove decimals (if exist)
     * @return Int value
     */
    fun RemoveDecimalValue(value: Double)
    {
        var index: Int = value.toString().indexOf(".");

        if (index == -1) return parseInt(value);
        return parseInt(String(value).substring(0, index));
    }
}