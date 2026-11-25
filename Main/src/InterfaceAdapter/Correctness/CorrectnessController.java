package InterfaceAdapter.Correctness;

import UseCase.Correctness.CorrectnessInputData;
import UseCase.Correctness.CorrectnessInputBoundary;

public class CorrectnessController {

    private final CorrectnessInputBoundary interactor;

    public CorrectnessController(CorrectnessInputBoundary Interactor){
        this.interactor = Interactor;
    }

    public void execute(int row, int column, int input){
        CorrectnessInputData inputData = new CorrectnessInputData(row, column, input);

        interactor.execute(inputData);
    }

}
