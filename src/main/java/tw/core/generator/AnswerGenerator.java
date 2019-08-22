package tw.core.generator;

import com.google.inject.Inject;
import tw.core.Answer;
import tw.core.exception.AnswerFormatIncorrectException;
import tw.core.validator.InputValidator;

/**
 * Created by jxzhong on 2017/5/17.
 */
public class AnswerGenerator {
    private final RandomIntGenerator randomIntGenerator;

    @Inject
    public AnswerGenerator(RandomIntGenerator randomIntGenerator) {
        this.randomIntGenerator = randomIntGenerator;
    }

    public Answer generate() throws AnswerFormatIncorrectException {
        String randomNumStr = this.randomIntGenerator.generateNums(9, 4);
        InputValidator inputValidator = new InputValidator();
        Boolean isValidate = inputValidator.validate(randomNumStr);
        if(!isValidate){
            throw new AnswerFormatIncorrectException("Answer format is incorrect");
        }

        Answer answer = Answer.createAnswer(randomNumStr);
        return answer;
    }
}
