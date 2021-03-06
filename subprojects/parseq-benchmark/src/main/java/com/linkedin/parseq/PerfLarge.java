/* $Id$ */
package com.linkedin.parseq;

import java.util.ArrayList;
import java.util.List;

import com.linkedin.parseq.Task;
import com.linkedin.parseq.Tasks;


/**
 * @author Jaroslaw Odzga (jodzga@linkedin.com)
 */
public class PerfLarge extends AbstractBenchmark {

  public static void main(String[] args) throws Exception {
//    FullLoadBenchmarkConfig cfg = new FullLoadBenchmarkConfig();
    ConstantThroughputBenchmarkConfig cfg = new ConstantThroughputBenchmarkConfig();
    cfg.CONCURRENCY_LEVEL = Integer.MAX_VALUE;
    cfg.events = 1000;
    new PerfLarge().runExample(cfg);
  }

  @Override
  Task<?> createPlan() {
    List<Task<?>> l = new ArrayList<>();
    for (int i = 0; i < 20; i++) {
      l.add(ioTask());
    }
    return Tasks.par(l);
  }

  private Task<?> task() {
    return Task.value("kldfjlajflskjflsjfslkajflkasj").map("length", s -> s.length()).map("+1", s -> s + 1)
        .map("+2", s -> s + 2).map("+3", s -> s + 3)
        .flatMap(x -> Task.value(x * 40)).map(x -> x -10);
  }

  private Task<?> ioTask() {
    return Task.value("kldfjlajflskjflsjfslkajflkasj")
            .flatMap("IO", x -> AsyncIOTask.getAsyncIOTask())
            .flatMap("IO2", x -> AsyncIOTask.getAsyncIOTask())
            .flatMap("IO3", x -> AsyncIOTask.getAsyncIOTask())
            .flatMap("IO4", x -> AsyncIOTask.getAsyncIOTask())
            .flatMap("IO5", x -> AsyncIOTask.getAsyncIOTask())
            .flatMap(x -> Task.value(x * 40))
            .map(x -> x -10);
  }
}
