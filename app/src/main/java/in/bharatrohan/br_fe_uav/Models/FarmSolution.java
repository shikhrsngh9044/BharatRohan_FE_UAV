package in.bharatrohan.br_fe_uav.Models;

public class FarmSolution {
    private int solNo;
    private String sol;

    public FarmSolution(int solNo, String sol) {
        this.solNo = solNo;
        this.sol = sol;
    }

    public int getSolNo() {
        return solNo;
    }

    public String getSol() {
        return sol;
    }
}
