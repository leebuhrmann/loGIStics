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
        onset: "StartTime1",
        expires: "ExpireTime1",
        headline: "Rain",
        description: "Description 1"
      },
      {
        event: "Test Alert2",
        onset: "StartTime2",
        expires: "ExpireTime2",
        headline: "Snow",
        description: "Description 2"
      },
    ];

    render(
      <RecoilRoot>
        <SideInfoCommon data={mockData} />
      </RecoilRoot>
    );

    const alert1 = screen.getAllByText("Test Alert1");
    expect(alert1.length).toBeGreaterThan(0);

    const time1 = screen.getAllByText("Time1");
    expect(time1.length).toBeGreaterThan(0);

    const snowOccurrences = screen.getAllByText("Snow");
    expect(snowOccurrences.length).toBeGreaterThanOrEqual(0);

    const alert2 = screen.getAllByText("Test Alert 2");
    expect(alert2.length).toBeGreaterThan(0);

    const time2 = screen.getAllByText("Time2");
    expect(time2.length).toBeGreaterThan(0);
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
