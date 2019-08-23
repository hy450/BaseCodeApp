package kr.smobile.core.util;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;

import java.util.concurrent.Callable;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * RxActivityIndicator will make onNext with 'true' value when there is at least one operation in progress.
 * When all operations complete, 'false' value will be sent.
 */
public class RxActivityIndicator extends Observable<Boolean> {
    private final BehaviorSubject<Integer> variable = BehaviorSubject.createDefault(0);

    private Observable<Boolean> loading;

    public RxActivityIndicator() {
        loading = variable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturnItem(0)
                .map(x -> x > 0)
                .share();
    }

    private <T> Observable<T> trackActivity(Observable<T> source) {
        Callable<Unit> resourceFactory = () -> {
            increment();
            return Unit.getDefault();
        };

        Function<Unit, Observable<T>> observableFactory = x -> source;

        Consumer<Unit> disposer = unit -> decrement();

        return Observable.using(resourceFactory, observableFactory, disposer);
    }

    @Override
    protected void subscribeActual(Observer<? super Boolean> observer) {
        loading.subscribe(observer);
    }

    private void increment() {
        variable.onNext(variable.getValue() + 1);
    }

    private void decrement() {
        variable.onNext(variable.getValue() - 1);
    }

    public void clearTrack() {
        variable.onNext(0);
    }

    public <T> ObservableTransformer<T,T> trackActivity() {
        return this::trackActivity;
    }

    public LiveData<Boolean> asLiveData() {
        return LiveDataReactiveStreams.fromPublisher(this.toFlowable(BackpressureStrategy.MISSING));
    }
}

class Unit implements Comparable<Unit> {
    private static final Unit defaultUnit = new Unit();

    @Override
    public int compareTo(@NonNull Unit o) {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Unit;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    static Unit getDefault() {
        return defaultUnit;
    }
}