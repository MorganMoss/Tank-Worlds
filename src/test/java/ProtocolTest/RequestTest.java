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
 @Test
 public void testSerializationRepair() {
  Request request = new Request("mfundo","repair");
  assertEquals("{\"clientName\":\"mfundo\",\"command\":\"repair\"}",request.serialize());

 }
 @Test
 public void testSerializationLeft() {
  Request request = new Request("morgan","left");
  assertEquals("{\"clientName\":\"morgan\",\"command\":\"left\"}",request.serialize());

 }
 @Test
 public void testSerializationDump() {
  Request request = new Request("mfundo","dump");
  assertEquals("{\"clientName\":\"mfundo\",\"command\":\"dump\"}",request.serialize());

 }
 @Test
 public void testSerializationReload() {
  Request request = new Request("maggie","reload");
  assertEquals("{\"clientName\":\"maggie\",\"command\":\"reload\"}",request.serialize());

 }
 @Test
 public void testSerializationRight() {
  Request request = new Request("sisipho","right");
  assertEquals("{\"clientName\":\"sisipho\",\"command\":\"right\"}",request.serialize());

 }
 @Test
 public void testSerializationRobot() {
  Request request = new Request("mfundo","robot");
  assertEquals("{\"clientName\":\"mfundo\",\"command\":\"robot\"}",request.serialize());

 }
 @Test
 public void testSerializationState() {
  Request request = new Request("morgan","state");
  assertEquals("{\"clientName\":\"morgan\",\"command\":\"state\"}",request.serialize());

 }


}
