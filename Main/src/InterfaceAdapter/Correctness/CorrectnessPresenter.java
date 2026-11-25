package InterfaceAdapter.Correctness;

import InterfaceAdapter.NewGame.SudokuViewModel;
import UseCase.Correctness.CorrectnessOutputData;
import UseCase.Correctness.CorrectnessOutputBoundary;


public class CorrectnessPresenter implements CorrectnessOutputBoundary{

    private final SudokuViewModel viewModel;

    public CorrectnessPresenter(SudokuViewModel viewModel){
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(CorrectnessOutputData correctnessOutputData){
        viewModel.showCorrectness(
                correctnessOutputData.getRow(),
                correctnessOutputData.getColumn(),
                correctnessOutputData.isCorrect());
    }
}
