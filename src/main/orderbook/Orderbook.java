import java.util.*;

class Order {
    int quantity;
    double price;
    String side;

    Order(int quantity, double price, String side) {
        this.quantity = quantity;
        this.price = price;
        this.side = side;
    }
}

final class Orderbook {
    private final TreeMap<Double, Deque<Order>> bids = new TreeMap<>(Collections.reverseOrder());
    private final TreeMap<Double, Deque<Order>> asks = new TreeMap<>();

    public Orderbook(boolean generateDummies) {
        if (generateDummies) {
            Random random = new Random(12);
            for (int i = 0; i < 10; i++) {
                double randomPrice = 90.0 + random.nextInt(1001) / 100.0;
                addOrder(random.nextInt(100) + 1, randomPrice, "BUY");
                addOrder(random.nextInt(100) + 1, randomPrice, "BUY");
            }
            for (int i = 0; i < 10; i++) {
                double randomPrice = 100.0 + random.nextInt(1001) / 100.0;
                addOrder(random.nextInt(100) + 1, randomPrice, "SELL");
                addOrder(random.nextInt(100) + 1, randomPrice, "SELL");
            }
        }
    }

    public void addOrder(int qty, double price, String side) {
        Order order = new Order(qty, price, side.equals("BUY") ? "BUY" : "SELL");
        TreeMap<Double, Deque<Order>> book = side.equals("BUY") ? bids : asks;

        book.computeIfAbsent(price, k -> new ArrayDeque<>()).addLast(order);
    }

    public void print() {
        System.out.println("========== Orderbook =========");
        printLeg(asks, "ASK");

        double bestAsk = bestQuote("SELL");
        double bestBid = bestQuote("BUY");
        System.out.println("====== " + String.format("%.2f", (bestAsk - bestBid) / bestBid * 10000) + "bps ======");

        printLeg(bids, "BUY");
        System.out.println("==============================\n\n");
    }

    private void printLeg(TreeMap<Double, Deque<Order>> book, String side) {
        if (side.equals("ASK")) {
            for (Map.Entry<Double, Deque<Order>> entry : book.entrySet()) {
                printPriceLevel(entry.getKey(), entry.getValue(), "31");
            }
        } else {
            NavigableSet<Double> descendingKeys = book.navigableKeySet().descendingSet();
            for (Double key : descendingKeys) {
                printPriceLevel(key, book.get(key), "32");
            }
        }
    }

    private void printPriceLevel(double price, Deque<Order> orders, String color) {
        int totalQuantity = orders.stream().mapToInt(o -> o.quantity).sum();
        System.out.printf("\t\033[1;%smRs.%6.2f%5d\033[0m ", color, price, totalQuantity);


        for (int i = 0; i < totalQuantity / 10; i++) {
            System.out.print("█");
        }
        System.out.println();
    }

    private double bestQuote(String side) {
        TreeMap<Double, Deque<Order>> book = side.equals("BUY") ? bids : asks;
        return book.isEmpty() ? 0.0 : book.firstKey();
    }

    public OrderFill handleOrder(String type, int orderQuantity, String side, double price) {
        int unitsTransacted = 0;
        double totalValue = 0.0;

        TreeMap<Double, Deque<Order>> book = side.equals("BUY") ? asks : bids;

        Iterator<Map.Entry<Double, Deque<Order>>> iterator = book.entrySet().iterator();
        while (iterator.hasNext() && orderQuantity > 0) {
            Map.Entry<Double, Deque<Order>> entry = iterator.next();
            double priceLevel = entry.getKey();

            if (type.equals("LIMIT") && ((side.equals("BUY") && priceLevel > price)
                    || (side.equals("SELL") && priceLevel < price))) {
                break;
            }

            Deque<Order> orders = entry.getValue();
            Iterator<Order> orderIterator = orders.iterator();
            while (orderIterator.hasNext() && orderQuantity > 0) {
                Order order = orderIterator.next();

                if (order.quantity > orderQuantity) {
                    unitsTransacted += orderQuantity;
                    totalValue += orderQuantity * priceLevel;
                    order.quantity -= orderQuantity;
                    orderQuantity = 0;
                } else {
                    unitsTransacted += order.quantity;
                    totalValue += order.quantity * priceLevel;
                    orderQuantity -= order.quantity;
                    orderIterator.remove();
                }
            }

            if (orders.isEmpty()) {
                iterator.remove();
            }
        }

        if (type.equals("LIMIT") && orderQuantity > 0) {
            addOrder(orderQuantity, price, side);
        }

        return new OrderFill(unitsTransacted, totalValue);
    }
}

class OrderFill {
    int unitsTransacted;
    double totalValue;

    OrderFill(int unitsTransacted, double totalValue) {
        this.unitsTransacted = unitsTransacted;
        this.totalValue = totalValue;
    }

    public int getFilledQuantity() {
        return unitsTransacted;
    }
}
