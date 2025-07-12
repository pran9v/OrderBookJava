
# OrderBookJava

![Orderbook Screenshot](https://github.com/user-attachments/assets/9d49c282-1454-4ce3-ae80-597989833a0f)

**OrderBookJava** is a terminal-based implementation of an electronic order book for trading systems, written in Java. It provides a robust and efficient engine for processing buy and sell orders.

## Key Features

- **Types of Orders**:
  - Limit Order
  - Market Order

- **Order Management**:
  - Add new buy or sell orders.
  - Execute trades by matching available orders.

- **Matching Logic**:
  - Automates the matching of compatible buy and sell orders based on price-time priority.
  - Ensures trades are executed efficiently and accurately.

- **Optimized Performance**:
  - The implementation uses a combination of data structures to achieve optimal performance for core operations.
  - O(1) for order lookup, modification, or cancellation by `orderId` (using a `HashMap`).
  - O(log P) for adding an order or canceling an order that removes a price level, where P is the number of unique price levels (using a `TreeMap`).

## Benchmark Results

### Java Version

- Average Latency: ~215 ns
- Throughput: ~3.2 million orders/sec
- Matched Orders: ~50% (100k orders run with 50k already placed for matching)

### C++ Version (Optimized)

- Average Latency: ~81 ns
- Throughput: ~8.1 million orders/sec
- Matched Orders: ~49.88%

> The C++ implementation leverages low-level optimizations, reduced GC pressure, and tighter memory control, hence the performance boost.

## Project Structure

- **Core Classes**:
  - `Orderbook`: Manages the main operations for the order book, including adding, removing, and matching orders.
  - `Order`: Represents a single order with details like order type (buy/sell), price, and quantity.
  - `OrderBookApp`: The starting point of the program.
  - `OrderFill`: Represents matched order result.
  - `Helpers`: Utility class for benchmarking output.

- **Flow**:
  1. Orders are added to the system and placed into price-sorted queues.
  2. The matching engine identifies compatible buy and sell orders at the best prices.
  3. Matched orders are executed, and the corresponding quantities are removed from the book.

## Getting Started

### Prerequisites

- Java Development Kit (JDK): Version 11 or higher.
- Maven: For dependency management and building the project.

### Setup

1. Clone the repository:
    ```bash
    git clone https://github.com/pran9v/OrderBookJava.git
    cd OrderBookJava
    ```

2. Build the project using Maven:
    ```bash
    mvn clean install
    ```

3. Run the application:
    ```bash
    java -jar target/OrderBookJava.jar
    ```
