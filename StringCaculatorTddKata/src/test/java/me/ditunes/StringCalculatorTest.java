package me.ditunes;

import org.junit.Assert;
import org.junit.Test;

/**
 * you can view http://osherove.com/tdd-kata-1/ to get info about string caculator kata
 * Created by linhan on 16/6/9.
 */
public class StringCalculatorTest {

    @Test
    public void when_input_empty_string_then_return_0() {
        input_num_str_should_get_target_result("", 0);
    }

    @Test
    public void when_input_single_then_return_the_num() {
        input_num_str_should_get_target_result("1", 1);
        input_num_str_should_get_target_result("0", 0);
        input_num_str_should_get_target_result("333", 333);
    }

    @Test
    public void when_input_mult_nums_with_comma_return_the_sum_of_nums() {

        input_num_str_should_get_target_result("2,3", 5);
        input_num_str_should_get_target_result("4,9", 13);
        input_num_str_should_get_target_result("10,20", 30);

        input_num_str_should_get_target_result("2,3,4,4,4", 17);
        input_num_str_should_get_target_result("4,9,1,90", 104);
        input_num_str_should_get_target_result("0,0,1,2", 3);
    }

    @Test
    public void when_input_mult_nums_and_one_larger_than_1000_and_not_eq_1000_then_result_ignore_the_num_larger_than_1000() {
        input_num_str_should_get_target_result("1001,3", 3);
        input_num_str_should_get_target_result("//+\n1+1000+10003+5", 1006);
        input_num_str_should_get_target_result("//[++++]\n1++++1000++++10003++++5", 1006);
    }



    @Test
    public void when_intput_multi_nums_with_default_delimiter_then_return_the_sum_of_nums() {
        input_num_str_should_get_target_result("2\n3", 5);
        input_num_str_should_get_target_result("2\n3,6,7\n8", 26);
    }

    @Test
    public void when_input_single_num_with_delimiter_then_throw_exception() {
        input_single_num_with_delimiter_express_or_default_delimiter_will_throw_exception(",2\n");
        input_single_num_with_delimiter_express_or_default_delimiter_will_throw_exception("//;\n113;");
        input_single_num_with_delimiter_express_or_default_delimiter_will_throw_exception("//[******]\n******111");
    }

    @Test
    public void when_input_multi_num_with_self_defined_delimiter_then_return_sum_of_them() {
        input_num_str_should_get_target_result("//;\n1;2", 3);
        input_num_str_should_get_target_result("//+\n1+2+4+5", 12);
        input_num_str_should_get_target_result("//*\n1*2*4*5*0", 12);
        input_num_str_should_get_target_result("//****\n1****2****4****5****0", 12);
        input_num_str_should_get_target_result("//[*][$]\n1*2$4*5$0", 12);
        input_num_str_should_get_target_result("//[****][$$$$]\n1****2$$$$4****5$$$$0", 12);
        input_num_str_should_get_target_result("//[****][$$$$]\n1****2,4****5\n0", 12);
    }


    @Test
    public void when_input_single_negative_nums_then_throw_exception_and_msg_contains_the_num() {
        nums_str_contains_negative_should_throw_exception("-199","[-199]");
    }


    @Test
    public void when_input_multi_negative_nums_then_throw_exception_and_msg_contains_all_nums() {
        nums_str_contains_negative_should_throw_exception("-1,3,-5,0","[-1, -5]");
    }

    @Test
    public void when_input_multi_negative_nums_and_self_defined_delimiter_then_throw_exception_and_msg_contains_all_nums() {
        nums_str_contains_negative_should_throw_exception("//[&]\n-1&-3&0","[-1, -3]");
    }

    public void input_single_num_with_delimiter_express_or_default_delimiter_will_throw_exception(String numsStr) {
        try {
            //given
            doStringCalculate(numsStr);
            Assert.fail("should has exception");
        }catch (Exception e){
            Assert.assertTrue(e instanceof  RuntimeException);
            Assert.assertEquals("invalid nums str", e.getMessage());
            return ;
        }
        Assert.fail("should has invalid nus str exception,because the numsStr contains single num with delimiter");
    }


    public void input_num_str_should_get_target_result(String input, int expected) {
        Assert.assertEquals(expected, doStringCalculate(input));
    }

    public void nums_str_contains_negative_should_throw_exception(String input, String negativeNums) {
        try {
            doStringCalculate(input);
        }catch (Exception e){
            Assert.assertTrue(e instanceof  RuntimeException);
            Assert.assertEquals(String.format("invalid negative str %s", negativeNums), e.getMessage());
            return ;
        }
        Assert.fail("it should has negative nums exist exception because numsStr contains negative num");
    }

    private int doStringCalculate(String input) {
        StringCalculator caculator = new StringCalculator();
        return caculator.getSumFromInputNumsStr(input);
    }


}
