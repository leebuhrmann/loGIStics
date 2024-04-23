import { render, screen } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
import user from "@testing-library/user-event";
import { TextEncoder, TextDecoder } from "util";

Object.assign(global, { TextDecoder, TextEncoder });
jest.mock("../components/map/MapComponent");

describe("Main elements are rendered", () => {
  beforeEach(() => {
    user.setup();
    render(<Home />);
  });

  test("user account nav bar is rendered", () => {
    const navigationElement = screen.getByRole("listitem");
    expect(navigationElement).toBeInTheDocument();
  });

  test("map is rendered", () => {
    const mockMapComponent = screen.getByTestId("mockMapComponent");
    expect(mockMapComponent).toBeInTheDocument();
  });
});
