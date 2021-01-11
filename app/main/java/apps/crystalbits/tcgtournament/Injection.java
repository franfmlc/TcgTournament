package apps.crystalbits.tcgtournament;

import android.content.Context;

public class Injection {

    public static Model provideDataSource(Context context) {
        return new Model(context);
    }

    public static VM_Model provideMTGPDataSource(Context context) {
        VM_Model m = VM_Model.getInstance(context);
        return m;
    }

    public static Repository provideRepository(Context context) {
        return new Repository(context);
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        return new ViewModelFactory(provideRepository(context));
    }
}