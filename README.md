# ZJ_HandlerThread

------
``` java
mHandlerThread = new HandlerThread("handler-Thread");
        mHandlerThread.start();
        mAsyncHandler = new Handler(mHandlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                try {
                    //当前是在子线程(handler-Thread)中执行
                    Thread.sleep(2000);

                    mMainThreadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            txtContent.setText(String.valueOf(new Random().nextInt()));
                        }
                    });

                    //发送消息, 通知更新
                    mAsyncHandler.sendEmptyMessage(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

> **1**、首先Looper.prepare()在本线程中保存一个Looper实例，然后该实例中保存一个MessageQueue对象；因为Looper.prepare()在一个线程中只能调用一次，所以MessageQueue在一个线程中只会存在一个。

> **2**、Looper.loop()会让当前线程进入一个无限循环，不端从MessageQueue的实例中读取消息，然后回调msg.target.dispatchMessage(msg)方法。

> **3**、Handler的构造方法，会首先得到当前线程中保存的Looper实例，进而与Looper实例中的MessageQueue想关联。

> **4**、Handler的sendMessage方法，会给msg的target赋值为handler自身，然后加入MessageQueue中。

> **5**、在构造Handler实例时，我们会重写handleMessage方法，也就是msg.target.dispatchMessage(msg)最终调用的方法。

> 在Activity中，我们并没有显示的调用Looper.prepare()和Looper.loop()方法，为啥Handler可以成功创建呢，这是因为在Activity的启动代码中，已经在当前UI线程调用了Looper.prepare()和Looper.loop()方法。

