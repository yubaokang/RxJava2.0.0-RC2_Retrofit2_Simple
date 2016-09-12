
# subscribe(T t);参数 T 说明

* public interface Consumer<T> {
      void accept(T t) throws Exception;
  }

 * public interface Subscriber<T> {
       public void onSubscribe(Subscription s);

       public void onNext(T t);

       public void onError(Throwable t);

       public void onComplete();
   }