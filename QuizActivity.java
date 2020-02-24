package edu.upenn.cis350.hw4;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import androidx.appcompat.app.AppCompatActivity;

public class QuizActivity extends AppCompatActivity {

    String difficulty;
    int[] numbers;
    int numQuestionsAnswered = 0;
    int numIncorrect = 0;
    int numConsecutive = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        difficulty = getIntent().getStringExtra("DIFFICULTY_LEVEL");
        numbers = getNumbers();
        displayNumbers();
    }

    public void onNumberButtonClick(View v) {
        Button numberButton = (Button) v;
        String numEntered = numberButton.getText().toString();
        TextView answerPastTV = (TextView) findViewById(R.id.answer);
        String answerPast = answerPastTV.getText().toString();
        String newAnswer = answerPast;
        int last = answerPast.lastIndexOf(" ");
        if (answerPast.indexOf("?") != -1) {
            newAnswer = answerPast.replace("?", numEntered);
        } else if (last + 2 == answerPast.length()) {
            newAnswer = answerPast.substring(0, last - 1)
                    + numEntered + answerPast.substring(last+1);
        } else if (last + 4 > answerPast.length()) {
            newAnswer = answerPast.substring(0, last) + numEntered + answerPast.substring(last+1);
        }
        answerPastTV.setText(newAnswer);
    }

    public void onClearButtonClick(View v) {
        TextView answerPastTV = (TextView) findViewById(R.id.answer);
        answerPastTV.setText("     ?");
    }

    public void onSubmitButtonClick(View v) {
        TextView answerTV = (TextView) findViewById(R.id.answer);
        String answerStr = answerTV.getText().toString();
        answerStr = answerStr.trim();
        try {
            int answer = Integer.parseInt(answerStr);
            if (answer == numbers[0] + numbers[1]) {
                correct();
            } else {
                incorrect();
            }
        } catch (Exception e) {
            incorrect();
        }
    }

    private int[] generateEasyNumbers() {
        int[] ret = new int[2];
        Random rand = new Random();
        ret[0] = rand.nextInt(90) + 10;
        int secondInt = rand.nextInt(90) + 10;
        boolean carryOverOnes = false;
        if ((ret[0] % 10) + (secondInt % 10) >= 10) {
            carryOverOnes = true;
        }
        boolean carryOverTens = false;
        if ((ret[0]/10 + secondInt/10 >= 10)) {
            carryOverTens = true;
        }
        while (carryOverOnes || carryOverTens) {
            secondInt = rand.nextInt(90) + 10;
            if ((ret[0] % 10) + (secondInt % 10) >= 10) {
                carryOverOnes = true;
            } else {
                carryOverOnes = false;
            }
            if ((ret[0]/10 + secondInt/10 >= 10)) {
                carryOverTens = true;
            } else {
                carryOverTens = false;
            }
        }
        ret[1] = secondInt;
        return ret;
    }

    private int[] generateMediumNumbers() {
        int[] ret = new int[2];
        Random rand = new Random();
        ret[0] = rand.nextInt(90) + 10;
        int secondInt = rand.nextInt(90) + 10;
        boolean carryOverOnes = false;
        if ((ret[0] % 10) + (secondInt % 10) >= 10) {
            carryOverOnes = true;
        }
        boolean carryOverTens = false;
        if ((ret[0] + secondInt)/100 > 0) {
            carryOverTens = true;
        }
        while ((carryOverOnes && carryOverTens) || (!carryOverOnes && !carryOverTens)) {
            secondInt = rand.nextInt(90) + 10;
            if ((ret[0] % 10) + (secondInt % 10) >= 10) {
                carryOverOnes = true;
            } else {
                carryOverOnes = false;
            }
            if ((ret[0] + secondInt)/100 > 0) {
                carryOverTens = true;
            } else {
                carryOverTens = false;
            }
        }
        ret[1] = secondInt;
        return ret;
    }

    private int[] generateHardNumbers() {
        int[] ret = new int[2];
        Random rand = new Random();
        ret[0] = rand.nextInt(90) + 10;
        int secondInt = rand.nextInt(90) + 10;
        boolean carryOverOnes = false;
        if ((ret[0] % 10) + (secondInt % 10) >= 10) {
            carryOverOnes = true;
        }
        boolean carryOverTens = false;
        if ((ret[0] + secondInt)/100 > 0) {
            carryOverTens = true;
        }
        while (!carryOverOnes || !carryOverTens) {
            secondInt = rand.nextInt(90) + 10;
            if ((ret[0] % 10) + (secondInt % 10) >= 10) {
                carryOverOnes = true;
            } else {
                carryOverOnes = false;
            }
            if ((ret[0] + secondInt)/100 > 0) {
                carryOverTens = true;
            } else {
                carryOverTens = false;
            }
        }
        ret[1] = secondInt;
        return ret;
    }

    private int[] getNumbers() {
        int[] numbers;
        if (difficulty.equals("Easy")) {
            numbers = generateEasyNumbers();
        } else if (difficulty.equals("Medium")) {
            numbers = generateMediumNumbers();
        } else {
            numbers = generateHardNumbers();
        }
        return numbers;
    }

    private void displayNumbers() {
        TextView firstNumTV = (TextView) findViewById(R.id.first_num);
        TextView secondNumTV = (TextView) findViewById(R.id.second_num);
        firstNumTV.setText("   " + numbers[0]);
        secondNumTV.setText("+ " + numbers[1]);
    }

    private void incorrect() {
        numIncorrect++;
        if (numIncorrect >= 3) {
            numConsecutive = 0;
            int correctAns = numbers[0] + numbers[1];
            TextView answerTV = (TextView) findViewById(R.id.answer);
            answerTV.setTextColor(Color.RED);
            String correctAnsStr;
            if (correctAns % 100 > 0) {
                correctAnsStr = "   " + correctAns;
            } else {
                correctAnsStr = "    " + correctAns;
            }
            answerTV.setText(correctAnsStr);
            Toast.makeText(getApplicationContext(), "Incorrect! The answer is "
                    + correctAns + ".", Toast.LENGTH_LONG).show();
            new AsyncTask<String, String, String>() {
                protected String doInBackground(String... inputs) {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {

                    }
                    return null;
                }

                protected void onPostExecute(String input) {
                    nextQuestion();
                }
            }.execute();
        } else {
            Toast.makeText(getApplicationContext(), "Incorrect! Try again!",
                    Toast.LENGTH_LONG).show();
            new AsyncTask<String, String, String>() {
                protected String doInBackground(String... inputs) {
                    try {
                        Thread.sleep(3000);
                    } catch (Exception e) {

                    }
                    return null;
                }

                protected void onPostExecute(String input) {
                    TextView answerTV = (TextView) findViewById(R.id.answer);
                    answerTV.setText("     ?");
                }
            }.execute();
        }
    }

    private void correct() {
        numConsecutive++;
        String msg;
        if (numConsecutive > 1) {
            msg = "Correct! " + numConsecutive + " in a row!";
        } else {
            msg = "Correct!";
        }
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        new AsyncTask<String, String, String>() {
            protected String doInBackground(String... inputs) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {

                }
                return null;
            }

            protected void onPostExecute(String input) {
                nextQuestion();
            }
        }.execute();
    }

    private void nextQuestion() {
        numQuestionsAnswered++;
        numIncorrect = 0;
        TextView answerTV = (TextView) findViewById(R.id.answer);
        numbers = getNumbers();
        displayNumbers();
        answerTV.setTextColor(Color.BLUE);
        answerTV.setText("     ?");
    }
}
