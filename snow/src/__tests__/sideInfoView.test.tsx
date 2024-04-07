import { render, screen, waitFor } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
jest.mock("../components/map/MapComponent");
import user from "@testing-library/user-event";
import SideInfoCommon from "@/components/side-menu/side-info-view/side-info-common";

describe("Side Info View", () => {
  beforeEach(() => {
    user.setup();
    render(<Home />);
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
      const boundaryTabElement = screen.getByRole("tab", {
        name: "Boundaries",
      });
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

  describe("Correct elements are rendered on tab interaction", () => {
    test("alerts content", async () => {
      const alertsContent = screen.getAllByText(/event/i);
      expect(alertsContent.length).toBeGreaterThan(0);
    });

    test("boundaries content", async () => {
      const boundariesTab = screen.getByRole("tab", { name: "Boundaries" });
      await user.click(boundariesTab);

      const boundariesContent = screen.getAllByText(/description/i);
      expect(boundariesContent.length).toBeGreaterThan(0);
    });
  });

  test("side-info-common alert data renders correctly", async () => {
    const mockData = [
      {
        title: "Test Alert1",
        sub: true,
        header: ["Time1"],
        body: ["Snow"],
      },
      {
        title: "Test Alert 2",
        sub: false,
        header: ["Time2"],
        body: ["Snow"],
      },
    ];

    render(
      <SideInfoCommon
        data={mockData}
        subCheckValue={false}
        setSubCheckValue={() => {}}
      />
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
        title: "Test Boundary 1",
        sub: true,
        header: ["Description"],
        body: ["Data 1"],
      },
      {
        title: "Test Boundary 2",
        sub: false,
        header: ["Description"],
        body: ["Data 2"],
      },
    ];

    render(
      <SideInfoCommon
        data={mockData}
        subCheckValue={false}
        setSubCheckValue={() => {}}
      />
    );

    const bound1 = screen.getAllByText("Test Boundary 1");
    expect(bound1.length).toBeGreaterThan(0);

    const des1 = screen.getAllByText("Description");
    expect(des1.length).toBeGreaterThan(0);

    const data1 = screen.getAllByText("Data 1");
    expect(data1.length).toBeGreaterThan(0);

    const bound2 = screen.getAllByText("Test Boundary 2");
    expect(bound2.length).toBeGreaterThan(0);

    const des2 = screen.getAllByText("Description");
    expect(des1.length).toBeGreaterThan(0);

    const data2 = screen.getAllByText("Data 2");
    expect(data1.length).toBeGreaterThan(0);

    // Need to figure out how to test checkbox states. Shadcn defines their checkboxes as butttons.
  });
});
