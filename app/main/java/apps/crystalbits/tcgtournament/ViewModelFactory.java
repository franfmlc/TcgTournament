package apps.crystalbits.tcgtournament;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import apps.crystalbits.tcgtournament.pairings.PairingsViewModel;
import apps.crystalbits.tcgtournament.util.RoundTimeManager;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final Repository mRepository;

    public ViewModelFactory(Repository repository) { mRepository = repository; }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(PairingsViewModel.class)) {
            return (T) new PairingsViewModel(mRepository, new RoundTimeManager());
        }

        //noinspection
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
