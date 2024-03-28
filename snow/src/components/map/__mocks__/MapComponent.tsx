import React from "react";

interface MockMapComponentProps {
    onPolygonComplete: () => void;
}

const MockMapComponent: React.FC<MockMapComponentProps> = ({
                                                               onPolygonComplete,
                                                           }) => {
    return (
        <div data-testid="mockMapComponent">
            Mocked Map Component
            <button data-testid="completePolygon" onClick={onPolygonComplete}>
                Complete Polygon
            </button>
        </div>
    );
};

export default MockMapComponent;
