import { render, screen } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
import user from "@testing-library/user-event";
import SideInfoCommon from "@/components/side-menu/side-info-view/side-info-common";
import { TextEncoder, TextDecoder } from "util";
import { RecoilRoot } from "recoil";
import { act } from "react-dom/test-utils";
import fetchMock from 'jest-fetch-mock';

Object.assign(global, { TextDecoder, TextEncoder });
jest.mock("../components/map/MapComponent");

describe("Side Info View", () => {
  beforeEach(() => {
    user.setup();
    fetchMock.resetMocks();
    render(
      <RecoilRoot>
        <Home />
      </RecoilRoot>
    );
  });

  describe("Side Info view elements are rendered", () => {
    test("search bar", () => {
      const searchElement = screen.getByRole("searchbox");
      expect(searchElement).toBeInTheDocument();
    });

    test("alerts tab", () => {
      const alertsTabElement = screen.getByRole("tab", { name: "Alerts" });
      expect(alertsTabElement).toBeInTheDocument();
    });

    test("boundary tab", () => {
      const boundaryTabElement = screen.getByRole("tab", { name: "Boundaries" });
      expect(boundaryTabElement).toBeInTheDocument();
    });

    test("search button", () => {
      const searchButtonElement = screen.getByTestId("search-button");
      expect(searchButtonElement).toBeInTheDocument();
    });

    test("subscriptions checkbox", () => {
      const checkboxElement = screen.getByRole("checkbox");
      expect(checkboxElement).toBeInTheDocument();
    });
  });

  test("side-info-common alert data renders correctly", async () => {
    const mockData = [
      {
        event: "Test Alert1",
        onset: "January 1, 1970",
        expires: "January 2, 1970",
        headline: "Rain",
        description: "Description:"
      },
      {
        event: "Test Alert2",
        onset: "January 3, 1970",
        expires: "January 4, 1970",
        headline: "Snow",
        description: "Description:"
      },
    ];

    render(
      <RecoilRoot>
        <SideInfoCommon data={mockData} />
      </RecoilRoot>
    );

    const alert1 = screen.getByText("Test Alert1");
    expect(alert1).toBeInTheDocument()

    const onset1 = screen.getAllByText(/January 1, 1970/i);
    expect(onset1.length).toBeGreaterThan(0);

    const expires1 = screen.getAllByText(/January 2, 1970/i);
    expect(expires1.length).toBeGreaterThan(0);

    const headline1 = screen.getAllByText("Rain");
    expect(headline1.length).toBeGreaterThanOrEqual(0);

    const alert2 = screen.getAllByText("Test Alert2");
    expect(alert2.length).toBeGreaterThan(0);

    const onset2 = screen.getAllByText(/January 3, 1970/i);
    expect(onset2.length).toBeGreaterThan(0);

    const expires2 = screen.getAllByText(/January 4, 1970/i);
    expect(expires2.length).toBeGreaterThan(0);

    const headline2 = screen.getAllByText("Snow");
    expect(headline2.length).toBeGreaterThanOrEqual(0);

  });

  test("side-info-common boundary data renders correctly", async () => {
    const mockData = [
      {
        name: "Test Boundary 1",
        description: "Description",
        subscribed: true,
      },
      {
        name: "Test Boundary 2",
        description: "Description",
        subscribed: false,
      },
    ];

    await act(async () => {
      render(
        <RecoilRoot>
          <SideInfoCommon data={mockData} />
        </RecoilRoot>
      );
    });

    const bound1 = screen.getByText(/Boundary 1/i);
    expect(bound1).toBeInTheDocument();

    const des1 = screen.getAllByText("Description");
    expect(des1.length).toBeGreaterThan(0);

    const bound2 = screen.getByText(/Test Boundary 2/i);
    expect(bound2).toBeInTheDocument();

    const des2 = screen.getAllByText("Description");
    expect(des2.length).toBeGreaterThan(0);

    const badgeElements = screen.getAllByText("Subscribed");
    expect(badgeElements.length).toBe(1);

  });
});
