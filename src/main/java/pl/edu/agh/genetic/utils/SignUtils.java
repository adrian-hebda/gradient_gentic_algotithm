package pl.edu.agh.genetic.utils;

import org.jetbrains.annotations.NotNull;
import pl.edu.agh.genetic.model.enums.Sign;

public class SignUtils {

    public static Sign determine(@NotNull Double number){
        if(number == 0.0) return Sign.ZERO;
        return number > 0.0 ? Sign.PLUS : Sign.MINUS;
    }

    public static Sign determine(@NotNull Integer number){
        if(number == 0) return Sign.ZERO;
        return number > 0 ? Sign.PLUS : Sign.MINUS;
    }
}
