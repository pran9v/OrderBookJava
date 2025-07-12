import java.util.Scanner;

public class OrderBookApp {

    public static void main(String[] args) {
        Orderbook ob = new Orderbook(true);
        Scanner scanner = new Scanner(System.in);
        System.out.println("""
                  ___          _           ____              _         __    _
                 / _ \\ _ __ __| | ___ _ __| __ )  ___   ___ | | __    / /   (_) __ ___   ____ _
                | | | | '__/ _` |/ _ \\ '__|  _ \\ / _ \\ / _ \\| |/ /   / /    | |/ _` \\ \\ / / _` |
                | |_| | | | (_| |  __/ |  | |_) | (_) | (_) |   <   / /     | | (_| |\\ V / (_| |
                 \\___/|_|  \\__,_|\\___|_|  |____/ \\___/ \\___/|_|\\_\\ /_/     _/ |\\__,_| \\_/ \\__,_|
                                                                          |__/
                """);

        while (true) {
            System.out.println("""
                    Options
                    -----------------------------------
                    |1. Print Orderbook               |
                    |2. Submit order                  |
                    |3. Run Benchmarks                |
                    |4. Exit                          |
                    -----------------------------------
                    Choice: """);

            int action = scanner.nextInt();

            if (action==1) {
                ob.print();

            } else if (action==2) {
                System.out.println("Enter order type:\n1. Market order\n2. Limit order\nSelection: ");
                int orderTypeInput = scanner.nextInt();
                String orderType = (orderTypeInput == 1) ? "MARKET" : "LIMIT";

                System.out.println("\nEnter side:\n1. Buy\n2. Sell\nSelection: ");
                int sideInput = scanner.nextInt();
                String side = (sideInput == 1) ? "BUY" : "SELL";

                System.out.println("\nEnter order quantity: ");
                int quantity = scanner.nextInt();

                if (orderType.equals("MARKET")) {
                    System.out.println(
                            "\nSubmitting market " + side.toLowerCase() + " order for " + quantity + " units..\n");

                    long startTime = System.nanoTime();
                    OrderFill fill = ob.handleOrder(orderType, quantity, side, 0.0);
                    long endTime = System.nanoTime();

                    Helpers.printFill(fill, quantity, startTime, endTime);

                } else if (orderType.equals("LIMIT")) {
                    System.out.println("\nEnter limit price: ");
                    double price = scanner.nextDouble();

                    System.out.println("\nSubmitting limit " + side.toLowerCase() + " order for " + quantity +
                            " units @ ₹" + price + "..\n");

                    long startTime = System.nanoTime();
                    OrderFill fill = ob.handleOrder(orderType, quantity, side, price);
                    long endTime = System.nanoTime();

                    Helpers.printFill(fill, quantity, startTime, endTime);
                }
                System.out.println();

            } else if (action==3) {
                System.out.println("\nRunning benchmarks (with matching)...\n");

                int orderCount = 100000;
                long totalLatencyNs = 0;
                int matchedOrders = 0;

                for (int i=0; i<50000; i++) {
                    ob.handleOrder("LIMIT", 1, "BUY", 100.0 + (i % 2));
                }

                long throughputStart = System.nanoTime();
                for (int i=0; i<orderCount; i++) {
                    String side = (i % 2 == 0) ? "SELL" : "BUY";
                    double price = 100.0 + (i % 2); 

                    long startTime = System.nanoTime();
                    OrderFill fill = ob.handleOrder("LIMIT", 1, side, price);
                    long endTime = System.nanoTime();

                    totalLatencyNs += (endTime - startTime);

                    if (fill != null && fill.unitsTransacted > 0) {
                        matchedOrders++;
                    }
                }
                long throughputEnd = System.nanoTime();

                double avgLatencyNs = totalLatencyNs / (double) orderCount;
                double avgLatencyUs = avgLatencyNs / 1_000.0;
                double avgLatencyMs = avgLatencyNs / 1000000.0;

                double totalTimeSec = (throughputEnd - throughputStart) / 1000000000.0;
                double throughput = orderCount / totalTimeSec;

                System.out.printf("Average Latency: %.3f ns (%.3f µs, %.6f ms)%n",
                        avgLatencyNs, avgLatencyUs, avgLatencyMs);
                System.out.printf("Throughput: %.2f orders/sec%n", throughput);
                System.out.printf("Matched Orders: %d / %d (%.2f%%)%n%n",
                        matchedOrders, orderCount, (matchedOrders * 100.0) / orderCount);

            } else if (action==4) {
                break;
            }
        }

        scanner.close();
        System.out.println("Program terminated.");
    }
}
