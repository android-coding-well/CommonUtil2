package com.gosuncn.cu.module.rxjava;

import android.os.Bundle;
import android.support.annotation.WorkerThread;

import com.gosuncn.core.base.BaseActivity;
import com.gosuncn.core.utils.L;
import com.gosuncn.cu.R;
import com.gosuncn.cu.bean.common.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * http://blog.chinaunix.net/uid-20771867-id-5187376.html
 */
public class RxjavaActivity extends BaseActivity {

    private static final String TAG = "RxjavaActivity";

    private Subscriber threadSubscriber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
    }

    private void test1() {
        //使用的rxjava的一个好处是有异常都会被onError捕获，而不会导致程序闪退
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                //onCompleted和onError只能调用一次，一旦调用，就不会执行subscriber的其他方法了
                subscriber.onNext("onNext1");
                subscriber.onNext("onNext2");
                subscriber.onNext("onNext3");
                produceException();
                subscriber.onNext("onNext4");
                //subscriber.onError(new Throwable("error"));
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                showShortToast("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                showShortToast(e.getMessage());
            }

            @Override
            public void onNext(String s) {
                L.e(TAG, s);
                showShortToast(s);
            }
        });
    }

    private void test2() {
        Observable.just(1, 2, 3, 4, 5).subscribe((integer) -> {
                    L.e(TAG, integer.toString());
                }
        );

        //与just不同的是from会将数组的元素一个一个just出去
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("a2");
        list.add("a3");
        Observable.from(list).subscribe(s -> {
                    L.e(TAG, s);
                }
        );

        Observable.defer(() -> {
                    //直接just得到的时间都是一样的，通过defer则可得到不同的时间
                    return Observable.just(System.currentTimeMillis(), System.currentTimeMillis());
                }
        ).subscribe((aLong) -> {
                    L.e(TAG, aLong.toString());
                }
        );
    }

    /**
     * 轮询、定时器
     */
    private void test3() {
        //周期定时器
        Observable<Long> observable = Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread());
        final Subscriber<Long> subscriber = new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Long aLong) {
                L.e(TAG, aLong.toString());
            }
        };
        observable.subscribe(subscriber);

        //轮询
        Subscription subscription = Schedulers.newThread().createWorker().schedulePeriodically(() -> {
                    L.e(TAG, "轮询");
                }
                , 1000, 2000, TimeUnit.MILLISECONDS);

        //定时器
        Observable.timer(6, TimeUnit.SECONDS).subscribe(aLong -> {
                    subscriber.unsubscribe();
                    subscription.unsubscribe();
                }
        );
    }


    private void test4() {
        //对数据进行加工
        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("a2");
        list.add("a3");
        Observable.from(list).map(new Func1<String, Person>() {
            @Override
            public Person call(String s) {
                Person person = new Person();
                person.name = s + "map";
                return person;
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Person>() {
            @Override
            public void call(Person person) {
                L.e(TAG, "map加工之后：" + person.name);
            }
        });
        //使用lambda后可以简化成这样
        Observable.from(list).map((s) -> {
            Person person = new Person();
            person.name = s + "map";
            if ("a2map".equals(person.name)) {
                produceException();
            }
            return person;
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe((person) -> {
                    L.e(TAG, "map加工之后：" + person.name);
                }, e -> {
                    L.e(TAG, "map加工错误：" + e.getMessage());
                });
    }

    private void produceException() {
        throw new RuntimeException("null pointer");
    }

    private void test5() {

        List<String> list = new ArrayList<>();
        list.add("a1");
        list.add("a2");
        list.add("a2");
        list.add("a3");
        list.add("a4");
        list.add("a4");
        list.add("a3");
        list.add("a4");
        list.add("a4");
        list.add("a1");
        list.add("a5");
        Observable.from(list).distinct().subscribe((s) -> {
            L.e(TAG, "去重之后（所有数据不会重复）：" + s);
        });
        Observable.from(list).distinctUntilChanged().subscribe((s) -> {
            L.e(TAG, "连续去重之后（数据相邻的不会重复）：" + s);
        });

    }

    private void test6() {
        Observable.just(1, 2, 3, 4, 9, 8, 6, 7, 5).filter((integer) -> {
            return integer > 5;
        }).subscribe((integer) -> {
            L.e(TAG, "过滤后：" + integer);
        });

    }


    private void test7() {
        //当做线程使用
        Observable.create(subscriber -> {
            needWorkInThread();
            subscriber.onNext("1");
            subscriber.onCompleted();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(threadSubscriber = new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {
                        L.e(TAG, "线程执行完毕");
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Object o) {
                        L.e(TAG, "线程执行结果：" + o.toString());
                    }
                });
    }


    public void test8() {
        //请求网络，第一种请求方式
        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                needWorkInThread();
                return null;
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                    }
                });

        //第二种请求方式
        Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return null;
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleSubscriber<String>() {
                    @Override
                    public void onSuccess(String value) {

                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });

        //PublishSubject相当于一个管道，数据从一边进马上从一边出，debounce则是去抖动，意思是在规定的时间之后订阅者才能收到信息
        PublishSubject publishSubject = PublishSubject.create();
        publishSubject.onNext("");//可用于点击事件，防止多次点击
        publishSubject.debounce(400, TimeUnit.MILLISECONDS)
                .subscribe(new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Object o) {

                    }
                });
    }

    @WorkerThread
    public void needWorkInThread() {
        L.e(TAG, "执行线程");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        //退出时关闭线程
        if (threadSubscriber != null) {
            L.e(TAG, "关闭线程");
            threadSubscriber.unsubscribe();
        }
        super.onDestroy();
    }
}
