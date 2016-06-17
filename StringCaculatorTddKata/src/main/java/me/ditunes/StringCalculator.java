package me.ditunes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Created by linhan on 16/6/9.
 */
public class StringCalculator {



    public static final int SHOULD_IGNORE_NUM_WHEN_ADD_IT = 1000;

    public static final String DIGITAL_REGEX = "-?\\d+";

    public int getSumFromInputNumsStr(String numsStr) {
        if (isEmptyStr(numsStr)) {
            return getSumOfEmptyStr();
        }
        String delimiterRegex = new DelimiterRegexBuilder(numsStr).createDelimiterRegex();
        numsStrShouldNotOnlyContainSingleNumAndDelimiter(numsStr, delimiterRegex);
        if (isNumsStrOnlyContainSingleNumWithoutDelimiters(numsStr, delimiterRegex)) {
            return getSumOfOneNum(numsStr);
        }
        return getSumOfMultNumsWithDelimiters(numsStr, delimiterRegex);
    }

    private boolean isNumsStrOnlyContainSingleNumWithoutDelimiters(String numsStr, String delimiterRegex) {
        return isNumsStrContainSingleNum(numsStr) && !isNumsStrContainDelimiter(numsStr,delimiterRegex);
    }

    private int getSumOfOneNum(String numStr) {
        return countSumOfNumsList(shouldNotExistNegativeNums(Collections.singletonList(numStr)));
    }

    private void numsStrShouldNotOnlyContainSingleNumAndDelimiter(String numsStr, String delimiterRegex) {
        if (isNumsStrContainSingleNum(numsStr) && isNumsStrContainDelimiter(numsStr, delimiterRegex)) {
            throw new RuntimeException("invalid nums str");
        }
    }

    private boolean isNumsStrContainSingleNum(String numsStr) {
        Matcher singelNumWithDelimiterMatcher = Pattern.compile(DIGITAL_REGEX).matcher(numsStr);
        int theAmountOfNumInStr = 0;
        while (singelNumWithDelimiterMatcher.find()) {
            theAmountOfNumInStr++;
            if (theAmountOfNumInStr > 1) {
                return false;
            }
        }
        return theAmountOfNumInStr == 1;

    }

    public boolean isNumsStrContainDelimiter(String numsStr, String delimiterRegex) {
        Matcher delimiterMatcher = Pattern.compile(delimiterRegex).matcher(numsStr);
        while (delimiterMatcher.find()) {
            return true;
        }
        return false;
    }

    private boolean isEmptyStr(String s) {
        return s == null || s.equals("");
    }

    private int getSumOfEmptyStr() {
        return 0;
    }

    private int getSumOfMultNumsWithDelimiters(String numsStr, String delimiterRegex) {
        numsStr = clearUserDefinedDelimiterExpression(numsStr);
        String[] splitNumByDelimiter = splitNumsStrByDelimiterRegex(numsStr, delimiterRegex);
        return countSumOfNumsList(shouldNotExistNegativeNums(filtUnNumStrFromStrArray(splitNumByDelimiter)));
    }

    private List<String> filtUnNumStrFromStrArray(String[] splitNumByDelimiter) {
        return Arrays.stream(splitNumByDelimiter).filter((strArrayItem) -> {
            Pattern digital = Pattern.compile("-?\\d+");
            return digital.matcher(strArrayItem).matches();
        }).collect(Collectors.toList());
    }

    private int countSumOfNumsList(List<String> numsStrList) {
        return numsStrList.stream().reduce(0, (total, numInArray) -> {
            Integer numItem = Integer.parseInt(numInArray);
            return total + (numItem > SHOULD_IGNORE_NUM_WHEN_ADD_IT ? 0 : numItem);
        }, (totalFirst, totalSecond) -> {
            return totalFirst + totalSecond;
        });
    }

    private List<String> shouldNotExistNegativeNums(List<String> numsStrList) {
        List<Integer> negativeNums = numsStrList.stream().map((numStr) -> {
            return Integer.parseInt(numStr);
        }).filter((num) -> {
            return num < 0;
        }).collect(Collectors.toList());
        if (negativeNums.isEmpty()) {
            return numsStrList;
        }
        throw raiseNegativeNumExistException(negativeNums.toArray(new Integer[negativeNums.size()]));
    }

    private RuntimeException raiseNegativeNumExistException(Integer[] negatives) {
        return new RuntimeException(String.format("invalid negative str %s", Arrays.toString(negatives)));
    }


    private String[] splitNumsStrByDelimiterRegex(String numsStr, String delimiterRegex) {
        return numsStr.split(delimiterRegex);
    }

    private String clearUserDefinedDelimiterExpression(String numsStr) {
        return numsStr.replaceAll(DelimiterRegexBuilder.DELIMITER_EXPRESSION, "");
    }



    public static class DelimiterRegexBuilder {

        public static final String DELIMITER_EXPRESSION = "//(.+)\n";

        public static final String DEFAULT_DELIMITER = ",|\n";

        public static final String USER_DEFINED_DELIMITER_WITH_BRACKET = "\\[(.+?)\\]";

        public static final String USER_DEFINED_DELIMITER_WITHOUT_BRACKET = "(.+)";

        private String numsStr;

        public DelimiterRegexBuilder(String numsStr) {
            this.numsStr = numsStr;
        }

        public String createDelimiterRegex() {
            String delimiterExpression = retrieveDelimiterDefineExpressionFromNumsStr();
            if (delimiterExpression == null) {
                return DEFAULT_DELIMITER;
            }
            List<String> delimiters = retrieveDelimitersFromDelimiterDefinedExpression(delimiterExpression);
            return joinUserDefinedDelimiterWithDefaultDelimiter(delimiters);
        }

        private String joinUserDefinedDelimiterWithDefaultDelimiter(List<String> delimiters) {
            if (delimiters.isEmpty()) {
                return DEFAULT_DELIMITER;
            }
            return Arrays.stream(delimiters.toArray(new String[delimiters.size()])).reduce(DEFAULT_DELIMITER, (item,regex) -> {
                regex += "|" + item;
                return regex;
            });
        }

        private List<String> retrieveDelimitersFromDelimiterDefinedExpression(String delimiterDefinedExpression) {
            List<String> delimiters = new ArrayList<String>();
            Arrays.stream(new String[]{USER_DEFINED_DELIMITER_WITH_BRACKET, USER_DEFINED_DELIMITER_WITHOUT_BRACKET}).anyMatch((item)->{
                System.out.println(item);
                Matcher matcher = Pattern.compile(item).matcher(delimiterDefinedExpression);
                while (matcher.find()) {
                    String regex = Pattern.quote(matcher.group(1));
                    if(regex != null && !regex.isEmpty()){
                        delimiters.add(regex);
                    }
                }
                return delimiters.size() > 0;
            });
            return delimiters;
        }

        private String retrieveDelimiterDefineExpressionFromNumsStr() {
            Matcher userDefinedDelimiterExpressionMatcher = Pattern.compile(DELIMITER_EXPRESSION).matcher(numsStr);
            if (userDefinedDelimiterExpressionMatcher.find()) {
                return userDefinedDelimiterExpressionMatcher.group(1);
            }
            return null;
        }
    }
}
