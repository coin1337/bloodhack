package me.zero.alpine;

public interface EventBus {
   void subscribe(Object var1);

   void subscribeAll(Object... var1);

   void subscribeAll(Iterable<Object> var1);

   void unsubscribe(Object var1);

   void unsubscribeAll(Object... var1);

   void unsubscribeAll(Iterable<Object> var1);

   void post(Object var1);

   void attach(EventBus var1);

   void detach(EventBus var1);
}
