package ProtocolTest;

import org.junit.jupiter.api.Test;
import za.co.wethinkcode.robotworlds.protocol.Request;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RequestTest{
 @Test
 public void testSerializationForward() {
  Request request = new Request("sisipho","forward");
  assertEquals("{\"clientName\":\"sisipho\",\"command\":\"forward\"}",request.serialize());

 }
 @Test
 public void testSerializationFire() {
  Request request = new Request("maggie","fire");
  assertEquals("{\"clientName\":\"maggie\",\"command\":\"fire\"}",request.serialize());

 }
 @Test
 public void testSerializationLook() {
  Request request = new Request("morgan","look");
  assertEquals("{\"clientName\":\"morgan\",\"command\":\"look\"}",request.serialize());

 }
}
