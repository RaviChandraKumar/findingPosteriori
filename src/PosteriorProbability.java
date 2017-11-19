import java.text.DecimalFormat;

/**
 * Created by empathy on 11/18/17.
 */
public class PosteriorProbability {

    private static final Double[] INIT_P_0_H_I = {0.0, 0.10, 0.20, 0.40, 0.20, 0.10};

    private static final Double[] INIT_P_Q1_C_H_I = {0.0, 1.0, 0.75, 0.50, 0.25, 0.0};

    private static final Double[] INIT_P_Q1_L_H_I = {0.0, 0.0, 0.25, 0.50, 0.75, 1.0};

    private static Double[] TEMP_P_T_H_I = {0.0, 0.10, 0.20, 0.40, 0.20, 0.10};

    private static Double P_T_Q_C = 0.0;
    private static Double P_T_Q_L = 0.0;

    private static DecimalFormat formatter = new DecimalFormat("#.#####");

    public static void main(String[] args) {

        String givenSeqOfObservations = "";
        int noOfObservations = 0;

        if (null != args && args.length != 0) {
            givenSeqOfObservations = args[0];
            noOfObservations = givenSeqOfObservations.length();
        }

        System.out.println("Observation sequence Q: " + givenSeqOfObservations);
        System.out.println("Length of Q: " + noOfObservations);

        for (int i=1;i<=5;i++){
            P_T_Q_C += (INIT_P_Q1_C_H_I[i] * INIT_P_0_H_I[i]);
            P_T_Q_L = 1 - P_T_Q_C;
        }

        for (int j=0; j<=noOfObservations; j++){
            String subStringOfObservations = "";
            if(noOfObservations > 0){
                subStringOfObservations = givenSeqOfObservations.substring(0,j);
            }
            System.out.println("After Observation " + j + " : " + subStringOfObservations);
            if (j==0){
                for (int i=1;i<=5;i++){
                    System.out.println(" P(H"+i +" | Q) = " + formatter.format(INIT_P_0_H_I[i]));
                }
                System.out.println("Probability that the next candy we pick will be C, given Q: = " + formatter.format(P_T_Q_C));
                System.out.println("Probability that the next candy we pick will be L, given Q: = " + formatter.format(P_T_Q_L));
            }
            else
            {
                for (int i=1;i<=5;i++){
                    TEMP_P_T_H_I[i] = findProbability_Of_Hi_given_t_observations(i, subStringOfObservations.substring(j-1));
                    System.out.println(" P(H"+i +" | Q) = " + formatter.format(TEMP_P_T_H_I[i]));
                }
                replaceElementsInTEMP_P_T_H_I();
                P_T_Q_C = findProbability_t_Of_Q_t_plus_1_C();
                P_T_Q_L = 1 - P_T_Q_C;
                System.out.println("Probability that the next candy we pick will be C, given Q: = " + formatter.format(P_T_Q_C));
                System.out.println("Probability that the next candy we pick will be L, given Q: = " + formatter.format(P_T_Q_L));
            }
        }
    }

    private static Double findProbability_Of_Hi_given_t_observations(int i, String observations){

        if(observations.equalsIgnoreCase("C")){
            Double val = findProbability_t_Of_Q_t_plus_1_C();
            return INIT_P_Q1_C_H_I[i] * TEMP_P_T_H_I[i] / val;
        }

        Double val = findProbability_t_Of_Q_t_plus_1_L();
        return INIT_P_Q1_L_H_I[i] * TEMP_P_T_H_I[i] / val;
    }

    private static Double findProbability_t_Of_Q_t_plus_1_C(){
        Double val = 0.0;
        for (int i=1;i<=5;i++){
            val += (INIT_P_Q1_C_H_I[i] * INIT_P_0_H_I[i]);
        }
        return val;
    }

    private static Double findProbability_t_Of_Q_t_plus_1_L(){
        Double val = 0.0;
        for (int i=1;i<=5;i++){
            val += (INIT_P_Q1_L_H_I[i] * INIT_P_0_H_I[i]);
        }
        return val;
    }

    private static void replaceElementsInTEMP_P_T_H_I(){
        for (int i=1;i<=5;i++){
           INIT_P_0_H_I[i] = TEMP_P_T_H_I[i];
        }
    }

}