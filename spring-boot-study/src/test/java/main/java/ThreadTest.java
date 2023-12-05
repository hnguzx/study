package main.java;

import org.junit.jupiter.api.Test;

import java.math.RoundingMode;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ThreadTest {

    @Test
    public void testThread() {
        CompletableFuture.allOf(
                CompletableFuture.runAsync(new Thread(() -> {
                    Optional.ofNullable(new Object())
                            .ifPresent(coreBPTINTR -> {
                                System.out.println("BPTINTR");
                            });
                })),
                CompletableFuture.runAsync(new Thread(() -> {
                    Optional.ofNullable(new Object())
                            .ifPresent(coreBPTINTR -> {
                                System.out.println("BPTINTR");
                            });
                })),
                CompletableFuture.runAsync(new Thread(() -> {
                    Optional.ofNullable(new Object())
                            .ifPresent(coreCITACR -> {
                                System.out.println("CITACR");
                            });
                })),
                // todo 这里返回了null
                CompletableFuture.runAsync(new Thread(() -> {
                    Optional.ofNullable(null)
                            .ifPresent(coreDDTODOL -> {
                                System.out.println("DDTODOL");
                            });
                })),
                // todo 这里返回了null
                CompletableFuture.runAsync(new Thread(() -> {
                    Optional.ofNullable(null)
                            .ifPresent(coreDDTINTB -> {
                                System.out.println("DDTINTB");
                            });
                })),
                CompletableFuture.runAsync(new Thread(() -> {
                    Optional.ofNullable(new Object())
                            .ifPresent(coreCLTCLR -> {
                                System.out.println("CLTCLR");
                                Object object = new Object();
                                if (Objects.nonNull(object)) {
                                    System.out.println("CLTLMT");
                                }
                            });
                }))
        ).join();
    }
}
